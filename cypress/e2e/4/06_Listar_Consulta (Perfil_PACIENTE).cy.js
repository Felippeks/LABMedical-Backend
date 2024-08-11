const { setToken, getToken } = require('../../support/tokens');

describe('Listar consultas com perfil de paciente', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const consultaUrl = 'http://localhost:8081/api/consultas';
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
            cy.task('setToken', { role: 'PACIENTE', token: pacienteToken });
        });
    });

    it('deve retornar 403 ao listar consultas com perfil de paciente', () => {
        cy.task('getToken', 'PACIENTE').then((token) => {
            cy.request({
                method: 'GET',
                url: consultaUrl,
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                failOnStatusCode: false
            }).then((response) => {
                expect(response.status).to.eq(403);
                cy.log('Paciente não autorizado a listar consultas, conforme esperado.');
            });
        });
    });
});
