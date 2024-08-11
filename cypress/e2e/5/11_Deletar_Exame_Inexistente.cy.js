const { setToken, getToken } = require('../../support/tokens');

describe('Deletar exame inexistente- erro 404', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const examesUrl = 'http://localhost:8081/api/exames';
    let adminToken;
    const nonexistentExameId = 9999;

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
            cy.task('setToken', { role: 'ADMIN', token: adminToken });
        });
    });

    it('deve retornar 404 ao tentar deletar um exame que não existe', () => {
        cy.request({
            method: 'DELETE',
            url: `${examesUrl}/${nonexistentExameId}`,
            headers: {
                'Authorization': `Bearer ${adminToken}`
            },
            failOnStatusCode: false
        }).then((response) => {
            expect(response.status).to.eq(404);
            cy.log('Erro 404 retornado como esperado: exame não encontrado');
        });
    });
});
