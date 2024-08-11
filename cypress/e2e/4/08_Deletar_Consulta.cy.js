const { setToken, getToken } = require('../../support/tokens');

describe('Deletar consulta com perfil de admin', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const consultaUrl = 'http://localhost:8081/api/consultas';
    let adminToken;
    let createdConsultaId;

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
        cy.task('queryDatabase', `SELECT id_consulta FROM consultas LIMIT 1`).then((result) => {
            expect(result).to.have.length(1);
            createdConsultaId = result[0].id_consulta;
            cy.log(`Consulta ID selecionada: ${createdConsultaId}`);
        });
    });

    it('deve retornar 200 ao deletar uma consulta', () => {
        cy.request({
            method: 'DELETE',
            url: `${consultaUrl}/${createdConsultaId}`,
            headers: {
                'Authorization': `Bearer ${adminToken}`
            }
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body).to.have.property('mensagem', 'Consulta deletada com sucesso');
            cy.log('Consulta deletada com sucesso!');

            cy.task('queryDatabase', `SELECT * FROM consultas WHERE id_consulta = ${createdConsultaId}`).then((result) => {
                expect(result).to.have.length(0);
                cy.log(`Consulta ID ${createdConsultaId} não existe mais no banco de dados.`);
            });
        });
    });
});
