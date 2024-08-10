const { setToken, getToken } = require('../../support/tokens');

describe('Listar consultas com perfil de admin', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const consultaUrl = 'http://localhost:8081/api/consultas';
    let medicoToken;

    before(() => {
        cy.request({
            method: 'POST',
            url: loginUrl,
            body: {
                email: 'medico@example.com',
                password: 'medico'
            }
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body).to.have.property('token');
            medicoToken = response.body.token;
            cy.log(`Token para MEDICO: ${medicoToken}`);
            cy.task('setToken', { role: 'MEDICO', token: medicoToken });
        });
    });

    it('deve retornar 200 ao listar todas as consultas', () => {
        cy.task('getToken', 'MEDICO').then((token) => {
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