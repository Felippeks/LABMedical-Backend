const { faker } = require('@faker-js/faker');
const { setToken, getToken } = require('../../support/tokens');

describe('Criar exame com perfil de medico', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const exameUrl = 'http://localhost:8081/api/exames';
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

    it('deve retornar 403 ao criar um exame', () => {
        cy.task('getToken', 'PACIENTE').then((token) => {
            const nomeExame = faker.lorem.words(3);
            const dataExame = faker.date.future().toISOString().split('T')[0];
            const horarioExame = faker.date.recent().toISOString().split('T')[1].split('.')[0];
            const tipoExame = faker.random.alpha({ count: faker.datatype.number({ min: 4, max: 32 }) });
            const laboratorio = faker.company.name();
            const urlDocumento = faker.internet.url();
            const resultados = faker.lorem.sentence();

            cy.request({
                method: 'POST',
                url: exameUrl,
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                body: {
                    nomeExame,
                    dataExame,
                    horarioExame,
                    tipoExame,
                    laboratorio,
                    urlDocumento,
                    resultados,
                    pacienteId: 2
                },
                failOnStatusCode: false
            }).then((response) => {
                expect(response.status).to.eq(403);
                cy.log('Acesso Negado: Você não tem as permissões necessárias para acessar este recurso.');
            });
        });
    });
});
