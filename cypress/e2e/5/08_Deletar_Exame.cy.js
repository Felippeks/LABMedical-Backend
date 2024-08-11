const { setToken, getToken } = require('../../support/tokens');

describe('Deletar consulta com perfil de admin', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const examesUrl = 'http://localhost:8081/api/exames';
    let adminToken;
    let createdExameId;

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
        cy.task('queryDatabase', `SELECT id FROM exames LIMIT 1`).then((result) => {
            expect(result).to.have.length(1);
            createdExameId = result[0].id;
            cy.log(`Exame ID selecionada: ${createdExameId}`);
        });
    });

    it('deve retornar 200 ao deletar um exame', () => {
        cy.request({
            method: 'DELETE',
            url: `${examesUrl}/${createdExameId}`,
            headers: {
                'Authorization': `Bearer ${adminToken}`
            }
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body).to.have.property('mensagem', 'Exame deletado com sucesso');
            cy.log('Exame deletada com sucesso!');

            cy.task('queryDatabase', `SELECT * FROM exames WHERE id = ${createdExameId}`).then((result) => {
                expect(result).to.have.length(0);
                cy.log(`Exame ID ${createdExameId} não existe mais no banco de dados.`);
            });
        });
    });
});
