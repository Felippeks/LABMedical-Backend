const { setToken, getToken } = require('../../support/tokens');

describe('Listar os prontuários', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const prontuariosUrl = 'http://localhost:8081/api/pacientes/prontuarios';
    let pacienteToken;

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
            cy.log(`Token para PACIENTE: ${pacienteToken}`);
            cy.task('setToken', {role: 'PACIENTE', token: pacienteToken});
        });
    });
    it('deve retornar 403 ao listar todos os prontuarios', () => {
        cy.task('getToken', 'PACIENTE').then((token) => {
            cy.request({
                method: 'GET',
                url: prontuariosUrl,
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                failOnStatusCode: false
            }).then((response) => {
                expect(response.status).to.eq(403);
                cy.log(response.body);
                cy.log('Acesso Negado: Você não tem as permissões necessárias para acessar este recurso.');
            });
        });
    });
});
