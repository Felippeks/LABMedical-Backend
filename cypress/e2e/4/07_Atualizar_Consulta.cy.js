const { faker } = require('@faker-js/faker');
const { setToken, getToken } = require('../../support/tokens');

describe('Atualizar consulta com perfil de admin', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const consultaUrl = 'http://localhost:8081/api/consultas';
    const novoMotivoConsulta = faker.lorem.words(3);
    const novaDescricaoProblema = faker.lorem.sentence();
    const novaDataConsulta = '2023-09-01';
    const novoHorarioConsulta = '14:00:00';
    const novaMedicacaoReceitada = 'Analgésico';
    const novaDosagemPrecaucoes = 'Tomar um comprimido a cada 12 horas';
    let adminToken;
    let createdConsultaId;

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
            cy.task('setToken', {role: 'ADMIN', token: adminToken});
        });

        cy.task('queryDatabase', `SELECT id_consulta FROM consultas LIMIT 1`).then((result) => {
            expect(result).to.have.length(1);
            createdConsultaId = result[0].id_consulta;
            cy.log(`Consulta ID selecionada: ${createdConsultaId}`);
        });
    });

    it('deve retornar 200 ao atualizar uma consulta', () => {
        cy.request({
            method: 'PUT',
            url: `${consultaUrl}/${createdConsultaId}`,
            headers: {
                'Authorization': `Bearer ${adminToken}`
            },
            body: {
                motivoConsulta: novoMotivoConsulta,
                dataConsulta: novaDataConsulta,
                horarioConsulta: novoHorarioConsulta,
                descricaoProblema: novaDescricaoProblema,
                medicacaoReceitada: novaMedicacaoReceitada,
                dosagemPrecaucoes: novaDosagemPrecaucoes,
                pacienteId: 2
            }
        }).then((response) => {
            expect(response.status).to.eq(200);

            // Verificando as propriedades dentro de `dados`
            expect(response.body).to.have.property('dados');
            expect(response.body.dados).to.have.property('motivoConsulta', novoMotivoConsulta);
            expect(response.body.dados).to.have.property('descricaoProblema', novaDescricaoProblema);
            cy.log('Consulta atualizada com sucesso!');

            cy.task('queryDatabase', `SELECT motivo_consulta, descricao_problema
                                      FROM consultas
                                      WHERE id_consulta = ${createdConsultaId}`).then((result) => {
                expect(result).to.have.length(1);
                expect(result[0].motivo_consulta).to.eq(novoMotivoConsulta);
                expect(result[0].descricao_problema).to.eq(novaDescricaoProblema);
                cy.log(`Consulta atualizada verificada: ${novoMotivoConsulta}, ${novaDescricaoProblema}`);
            });
        });
    });
});
