const { setToken, getToken } = require('../../support/tokens');

describe('Listar os prontuários', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const prontuariosUrl = 'http://localhost:8081/api/pacientes/prontuarios';
    let medicoToken;

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
            medicoToken = response.body.token;
            cy.log(`Token para MEDICO: ${medicoToken}`);
            cy.task('setToken', {role: 'MEDICO', token: medicoToken});
        });
    });
    it('deve retornar 200 ao listar todos os prontuarios', () => {
        cy.task('getToken', 'MEDICO').then((token) => {
            cy.request({
                method: 'GET',
                url: prontuariosUrl,
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            }).then((response) => {
                expect(response.status).to.eq(200);
                expect(response.body).to.have.property('mensagem', 'Lista de pacientes recuperada com sucesso');
                cy.log(response.body);
            });
        });
    });
});
