const { setToken } = require('../../support/tokens');


// Teste para autenticar usuários e obter tokens
describe('Autenticação de Usuário', () => {
    const baseUrl = 'http://localhost:8081/api/usuarios/login';

    // Define os usuários e suas credenciais
    const users = [
        { email: 'admin@example.com', password: 'admin', role: 'ADMIN' },
        { email: 'medico@example.com', password: 'medico', role: 'MEDICO' },
        { email: 'paciente@example.com', password: 'paciente', role: 'PACIENTE' }
    ];

    users.forEach(user => {
        it(`deve autenticar ${user.role} e receber um token`, () => {
            cy.request({
                method: 'POST',
                url: baseUrl,
                body: {
                    email: user.email,
                    password: user.password
                }
            }).then((response) => {
                expect(response.status).to.eq(200);
                expect(response.body).to.have.property('token');
                const token = response.body.token;
                cy.log(`Token para ${user.role}: ${token}`);
                cy.task('setToken', { role: user.role, token });
            });
        });
    });
});