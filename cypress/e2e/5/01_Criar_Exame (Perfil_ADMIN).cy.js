const { faker } = require('@faker-js/faker');
const { setToken, getToken } = require('../../support/tokens');

describe('Criar exame com perfil de admin', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const exameUrl = 'http://localhost:8081/api/exames';
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

    it('deve retornar 201 ao criar um exame', () => {
        cy.task('getToken', 'ADMIN').then((token) => {
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
                }
            }).then((response) => {
                expect(response.status).to.eq(201);
                cy.log('Exame criado com sucesso!');
            });
        });
    });
});
