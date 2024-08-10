
const { faker } = require('@faker-js/faker');
const { setToken, getToken } = require('../../support/tokens');

describe('Criar consulta com perfil de admin', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const consultaUrl = 'http://localhost:8081/api/consultas';
    let adminToken;

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
            cy.log(`Token para ADMIN: ${adminToken}`);
            cy.task('setToken', { role: 'ADMIN', token: adminToken });
        });
    });

    it('deve retornar 201 ao criar uma consulta', () => {
        cy.task('getToken', 'ADMIN').then((token) => {
            const motivoConsulta = faker.lorem.words(5);
            const dataConsulta = faker.date.future().toISOString().split('T')[0];
            const horarioConsulta = faker.date.recent().toISOString().split('T')[1].split('.')[0];
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
                }
            }).then((response) => {
                expect(response.status).to.eq(201);
                cy.log('Consulta criada com sucesso!');
            });
        });
    });
});
