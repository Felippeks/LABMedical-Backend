const { setToken, getToken } = require('../../support/tokens');

describe('Autenticação de Usuário e Gerenciamento de Pacientes', () => {
    const baseUrl = 'http://localhost:8081/api/usuarios/login';
    let adminToken;

    before(() => {
        cy.request({
            method: 'POST',
            url: baseUrl,
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

    it('deve retornar 404 ao acessar /api/pacientes/id com um ID inexistente', () => {
        const nonexistentPatientId = 9999;

        cy.request({
            method: 'GET',
            url: `http://localhost:8081/api/pacientes/${nonexistentPatientId}`,
            headers: {
                'Authorization': `Bearer ${adminToken}`
            },
            failOnStatusCode: false
        }).then((response) => {
            expect(response.status).to.eq(404);
            expect(response.body).to.have.property('mensagem', 'Paciente não encontrado');
        });
    });
});
