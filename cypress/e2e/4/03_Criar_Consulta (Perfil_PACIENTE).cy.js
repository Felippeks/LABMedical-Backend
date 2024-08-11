
const { faker } = require('@faker-js/faker');
const { setToken, getToken } = require('../../support/tokens');

describe('Criar consulta com perfil de paciente', () => {
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

    it('deve retornar 403 ao criar uma consulta', () => {
        cy.task('getToken', 'PACIENTE').then((token) => {
            const motivoConsulta = faker.lorem.words(5);
            const dataConsulta = faker.date.future().toISOString().split('T')[0];
            const horarioConsulta = faker.date.recent(1).toISOString().split('T')[1].split('.')[0];
            const descricaoProblema = faker.lorem.sentence();
            const medicacaoReceitada = faker.random.word();
            const dosagemPrecaucoes = `Tomar ${faker.datatype.number({ min: 1, max: 2 })} comprimido(s) a cada ${faker.datatype.number({ min: 4, max: 12 })} horas`;

            cy.request({
                method: 'POST',
                url: consultaUrl,
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                body: {
                    motivoConsulta,
                    dataConsulta,
                    horarioConsulta,
                    descricaoProblema,
                    medicacaoReceitada,
                    dosagemPrecaucoes,
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
