const { faker } = require('@faker-js/faker');
const { setToken, getToken } = require('../../support/tokens');

describe('Criar consulta com perfil de admin - erro 400', () => {
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

    it('deve retornar 400 ao criar uma consulta com motivoConsulta vazio', () => {
        cy.task('getToken', 'ADMIN').then((token) => {
            const motivoConsulta = "";
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
                },
                failOnStatusCode: false
            }).then((response) => {
                expect(response.status).to.eq(400);
                expect(response.body).to.have.property('motivoConsulta', 'Motivo da consulta é obrigatório');
                cy.log('Erro 400 retornado como esperado: Motivo da consulta é obrigatório');
            });
        });
    });
});
