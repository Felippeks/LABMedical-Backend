const { setToken, getToken } = require('../../support/tokens');

describe('Deletar consulta com perfil de paciente - erro 403', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const exameUrl = 'http://localhost:8081/api/exames';
    let pacienteToken;
    let createdExameId;

    before(() => {
        cy.request({
            method: 'POST',
            url: loginUrl,
            body: {
                email: 'paciente@example.com',
                password: 'paciente'
            }
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body).to.have.property('token');
            pacienteToken = response.body.token;
            cy.task('setToken', { role: 'PACIENTE', token: pacienteToken });
        });
        cy.task('queryDatabase', `SELECT id FROM exames LIMIT 1`).then((result) => {
            expect(result).to.have.length(1);
            createdExameId = result[0].id;
            cy.log(`Exame ID selecionado: ${createdExameId}`);
        });
    });

    it('deve retornar 403 ao tentar deletar uma consulta com perfil de paciente', () => {
        cy.request({
            method: 'DELETE',
            url: `${exameUrl}/${createdExameId}`,
            headers: {
                'Authorization': `Bearer ${pacienteToken}`
            },
            failOnStatusCode: false
        }).then((response) => {
            expect(response.status).to.eq(403);
            cy.log('Acesso Negado: Você não tem as permissões necessárias para acessar este recurso.');
        });
    });
});
