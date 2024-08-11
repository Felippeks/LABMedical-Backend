const { setToken, getToken } = require('../../support/tokens');

describe('Listar consultas com perfil de admin', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const consultaUrl = 'http://localhost:8081/api/consultas';
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

    it('deve retornar 200 ao listar todas as consultas', () => {
        cy.task('getToken', 'ADMIN').then((token) => {
            cy.request({
                method: 'GET',
                url: consultaUrl,
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            }).then((response) => {
                expect(response.status).to.eq(200);
                cy.log('Consultas listadas com sucesso!');
                cy.log(response.body);
            });
        });
    });
});
