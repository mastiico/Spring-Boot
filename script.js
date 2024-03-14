document.addEventListener('DOMContentLoaded', () => {
    const content = document.getElementById('content');
    const listBooksBtn = document.getElementById('list-books');
    const addBookBtn = document.getElementById('add-book');
    const bookSelect = document.getElementById('book-select');
    const editBookBtn = document.getElementById('edit-book');
    const deleteBookBtn = document.getElementById('delete-book');
    const viewBookBtn = document.getElementById('view-book'); // Adicionando o botão de visualização de livro

    // Função para carregar e exibir os livros no seletor de livros
    async function listAllBooks() {
        content.innerHTML = '';
        try {
            const response = await fetch('http://localhost:8080/book/v1');
            const books = await response.json();
            const bookCardsContainer = document.createElement('div');
            bookCardsContainer.classList.add('book-cards-container');
    
            books.forEach(book => {
                const card = document.createElement('div');
                card.classList.add('book-card');
    
                const title = document.createElement('p');
                title.classList.add('book-title');
                title.textContent = book.title;
                card.appendChild(title);
    
                const author = document.createElement('p');
                author.classList.add('book-author');
                author.textContent = `by ${book.author}`;
                card.appendChild(author);
    
                const editButton = document.createElement('button');
                editButton.textContent = 'Edit';
                editButton.addEventListener('click', () => editBook(book.id));
                card.appendChild(editButton);
    
                const deleteButton = document.createElement('button');
                deleteButton.textContent = 'Delete';
                deleteButton.addEventListener('click', () => deleteBook(book.id));
                card.appendChild(deleteButton);
    
                bookCardsContainer.appendChild(card);
            });
    
            content.appendChild(bookCardsContainer);
        } catch (error) {
            console.error('Error:', error);
            alert('Failed to fetch books. Please try again later.');
        }
    }
    

    // Função para exibir detalhes de um livro específico
    async function showBookDetails(bookId) {
        content.innerHTML = '';
        try {
            const response = await fetch(`http://localhost:8080/book/v1/${bookId}`);
            const book = await response.json();
            const detailsTemplate = document.getElementById('book-details-template').content;
            const bookDetails = detailsTemplate.querySelector('#book-details');
            bookDetails.innerHTML = ''; // Limpa os detalhes do livro existentes

            // Remove o campo "_links" do objeto do livro
            delete book._links;

            // Formata a data para DD/MM/YYYY
            const launchDate = new Date(book.launchDate);
            const formattedDate = `${launchDate.getDate().toString().padStart(2, '0')}/${(launchDate.getMonth() + 1).toString().padStart(2, '0')}/${launchDate.getFullYear()}`;

            // Adiciona os detalhes formatados ao card
            const keys = Object.keys(book);
            keys.forEach(key => {
                const p = document.createElement('p');
                if (key === 'launchDate') {
                    p.innerHTML = `<strong>${key}:</strong> ${formattedDate}`;
                } else {
                    p.innerHTML = `<strong>${key}:</strong> ${book[key]}`;
                }
                bookDetails.appendChild(p);
            });

            content.appendChild(document.importNode(detailsTemplate, true));
        } catch (error) {
            console.error('Error:', error);
            alert('Failed to fetch book details. Please try again later.');
        }
    }

    // Função para visualizar o livro pelo ID fornecido
    function viewBook() {
        const bookId = bookSelect.value;
        if (bookId.trim() !== '') {
            showBookDetails(parseInt(bookId));
        } else {
            alert('Please enter a valid book ID.');
        }
    }

    // Event listener para o botão "View Book"
    viewBookBtn.addEventListener('click', viewBook);

    // Função para editar o livro com o ID selecionado
    async function editBook(bookId) {
        try {
            // Buscar os detalhes do livro selecionado
            const response = await fetch(`http://localhost:8080/book/v1/${bookId}`);
            if (!response.ok) {
                throw new Error('Failed to fetch book details for editing');
            }
            const book = await response.json();

            // Exibir o formulário de edição de livro preenchido com os detalhes do livro
            const editBookForm = document.createElement('form');
            editBookForm.innerHTML = `
                <label for="edit-title">Title:</label><br>
                <input type="text" id="edit-title" value="${book.title}" required><br>
                <label for="edit-author">Author:</label><br>
                <input type="text" id="edit-author" value="${book.author}" required><br>
                <label for="edit-launchDate">Launch Date:</label><br>
                <input type="date" id="edit-launchDate" value="${book.launchDate.split('T')[0]}" required><br> <!-- Formatando a data -->
                <label for="edit-price">Price:</label><br>
                <input type="number" id="edit-price" value="${book.price}" required><br>
                <button type="submit">Update Book</button>
            `;

            // Adicionando o evento de submit ao formulário de edição
            editBookForm.addEventListener('submit', async (event) => {
                event.preventDefault();
                const editedBook = {
                    title: document.getElementById('edit-title').value,
                    author: document.getElementById('edit-author').value,
                    launchDate: document.getElementById('edit-launchDate').value + 'T00:00:00', // Formatando a data
                    price: parseFloat(document.getElementById('edit-price').value)
                };
                try {
                    const updateResponse = await fetch(`http://localhost:8080/book/v1/${bookId}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(editedBook)
                    });
                    if (!updateResponse.ok) {
                        throw new Error('Failed to update book');
                    }
                    alert('Book updated successfully');
                    // Recarregar a lista de livros após a atualização
                    listAllBooks();
                } catch (error) {
                    console.error('Error:', error);
                    alert('Failed to update book. Please try again later.');
                }
            });

            // Limpar o conteúdo anterior e adicionar o formulário de edição de livro
            content.innerHTML = '';
            content.appendChild(editBookForm);
        } catch (error) {
            console.error('Error:', error);
            alert('Failed to fetch book details for editing. Please try again later.');
        }
    }

    // Função para excluir o livro com o ID fornecido
    async function deleteBook(bookId) {
        try {
            const response = await fetch(`http://localhost:8080/book/v1/${bookId}`, {
                method: 'DELETE'
            });
            if (response.ok) {
                alert('Book deleted successfully');
                listAllBooks(); // Atualizar lista de livros após exclusão
            } else {
                throw new Error('Failed to delete book');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Failed to delete book. Please try again later.');
        }
    }

    // Event listener para o botão "Delete Book"
    deleteBookBtn.addEventListener('click', () => {
        const selectedBookId = bookSelect.value;
        deleteBook(selectedBookId);
    });
});
