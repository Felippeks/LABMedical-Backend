const { setToken, getToken } = require('../../support/tokens');

describe('Listar exames com perfil de admin', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const examesUrl = 'http://localhost:8081/api/exames';
    let adminToken;

    before(() => {
        cy.request({
            method: 'POST',
            url: loginUrl,
            body: {
                email: 'admin@example.com',
                password: 'admin'
            }
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body).to.have.property('token');
            adminToken = response.body.token;
            cy.log(`Token para ADMIN: ${adminToken}`);
            cy.task('setToken', { role: 'ADMIN', token: adminToken });
        });
    });

    it('deve retornar 200 ao listar todos os exames', () => {
        cy.task('getToken', 'ADMIN').then((token) => {
            cy.request({
                method: 'GET',
                url: examesUrl,
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            }).then((response) => {
                expect(response.status).to.eq(200);
                cy.log('Exames listadas com sucesso!');
                cy.log(response.body);
            });
        });
    });
});
