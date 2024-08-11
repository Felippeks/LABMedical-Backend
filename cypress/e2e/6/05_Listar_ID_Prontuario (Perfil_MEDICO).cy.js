const { setToken, getToken } = require('../../support/tokens');

describe('Listar os prontuários de um paciente específico', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const prontuariosUrl = (id) => `http://localhost:8081/api/pacientes/${id}/prontuarios`;
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
            cy.task('setToken', {role: 'MEDICO', token: medicoToken});
        });
    });

    it('deve retornar 200 ao listar os prontuarios de um paciente específico', () => {
        cy.task('queryDatabase', 'SELECT id FROM pacientes LIMIT 1')
            .then((result) => {
                const pacienteId = result[0].id;
                cy.task('getToken', 'ADMIN').then((token) => {
                    cy.request({
                        method: 'GET',
                        url: prontuariosUrl(pacienteId),
                        headers: {
                            'Authorization': `Bearer ${token}`
                        }
                    }).then((response) => {
                        expect(response.status).to.eq(200);
                        expect(response.body).to.have.property('mensagem', 'Prontuários do paciente recuperados com sucesso');
                        cy.log(response.body);
                    });
                });
            });
    });
});
