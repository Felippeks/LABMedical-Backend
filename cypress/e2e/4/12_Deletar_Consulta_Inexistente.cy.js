const { setToken, getToken } = require('../../support/tokens');

describe('Deletar consulta com perfil de admin - erro 404', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const consultaUrl = 'http://localhost:8081/api/consultas';
    let adminToken;
    const nonexistentConsultaId = 9999;

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

    it('deve retornar 404 ao tentar deletar uma consulta que não existe', () => {
        cy.request({
            method: 'DELETE',
            url: `${consultaUrl}/${nonexistentConsultaId}`,
            headers: {
                'Authorization': `Bearer ${adminToken}`
            },
            failOnStatusCode: false
        }).then((response) => {
            expect(response.status).to.eq(404);
            cy.log('Erro 404 retornado como esperado: Consulta não encontrada');
        });
    });
});
