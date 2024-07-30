const { setToken } = require('../../support/tokens');

// Teste para autenticar usuários com dados ausentes
describe('Autenticação de Usuário com Dados Ausentes', () => {
    const baseUrl = 'http://localhost:8081/api/usuarios/login';

    // Define os usuários e suas credenciais
    const users = [
        { email: 'admin@example.com', password: '', role: 'ADMIN' },
        { email: 'medico@example.com', password: '', role: 'MEDICO' },
        { email: 'paciente@example.com', password: '', role: 'PACIENTE' }
    ];

    users.forEach(user => {
        it(`não deve autenticar ${user.role} e deve retornar status 400`, () => {
            cy.request({
                method: 'POST',
                url: baseUrl,
                body: {
                    email: user.email,
                    password: user.password
                },
                failOnStatusCode: false
            }).then((response) => {
                expect(response.status).to.eq(404);
                expect(response.body).not.to.have.property('token');
                cy.log(`Usuário ${user.role} não autenticado, status: ${response.status}`);
            });
        });
    });
});