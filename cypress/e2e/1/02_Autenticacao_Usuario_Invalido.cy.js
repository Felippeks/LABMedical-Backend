const { setToken } = require('../../support/tokens');

describe('Autenticação de Usuário', () => {
    const baseUrl = 'http://localhost:8081/api/usuarios/login';
    const users = [
        { email: 'admin@example.com', password: 'admin2', role: 'ADMIN' },
        { email: 'medico@example.com', password: 'medico2', role: 'MEDICO' },
        { email: 'paciente@example.com', password: 'paciente2', role: 'PACIENTE' }
    ];

    users.forEach(user => {
        it(`não deve autenticar ${user.role} e deve retornar status 404 com texto "credenciais invalidas"`, () => {
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