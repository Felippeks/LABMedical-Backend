const { setToken } = require('../../support/tokens');

describe('User Authentication', () => {
    const baseUrl = 'http://localhost:8081/api/usuarios/login';

    const users = [
        { email: 'admin@example.com', password: 'admin', role: 'ADMIN' },
        { email: 'medico@example.com', password: 'medico', role: 'MEDICO' },
        { email: 'paciente@example.com', password: 'paciente', role: 'PACIENTE' }
    ];

    users.forEach(user => {
        it(`should authenticate ${user.role} and receive a token`, () => {
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
                cy.log(`Token for ${user.role}: ${token}`);
                cy.task('setToken', { role: user.role, token });
            });
        });
    });
});
