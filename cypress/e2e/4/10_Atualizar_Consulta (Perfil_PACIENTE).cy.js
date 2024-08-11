const { faker } = require('@faker-js/faker');
const { setToken, getToken } = require('../../support/tokens');

describe('Atualizar consulta com perfil de paciente - erro 403', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const consultaUrl = 'http://localhost:8081/api/consultas';
    const novoMotivoConsulta = faker.lorem.words(3);
    const novaDescricaoProblema = faker.lorem.sentence();
    const novaDataConsulta = '2023-09-01';
    const novoHorarioConsulta = '14:00:00';
    const novaMedicacaoReceitada = 'Analgésico';
    const novaDosagemPrecaucoes = 'Tomar um comprimido a cada 12 horas';
    let pacienteToken;
    let createdConsultaId;

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
            cy.task('setToken', { role: 'PACIENTE', token: pacienteToken });
        });

        cy.task('queryDatabase', `SELECT id_consulta FROM consultas LIMIT 1`).then((result) => {
            expect(result).to.have.length(1);
            createdConsultaId = result[0].id_consulta;
            cy.log(`Consulta ID selecionada: ${createdConsultaId}`);
        });
    });

    it('deve retornar 403 ao tentar atualizar uma consulta com perfil de paciente', () => {
        cy.request({
            method: 'PUT',
            url: `${consultaUrl}/${createdConsultaId}`,
            headers: {
                'Authorization': `Bearer ${pacienteToken}`
            },
            body: {
                motivoConsulta: novoMotivoConsulta,
                dataConsulta: novaDataConsulta,
                horarioConsulta: novoHorarioConsulta,
                descricaoProblema: novaDescricaoProblema,
                medicacaoReceitada: novaMedicacaoReceitada,
                dosagemPrecaucoes: novaDosagemPrecaucoes,
                pacienteId: 2
            },
            failOnStatusCode: false
        }).then((response) => {
            expect(response.status).to.eq(403);
            cy.log('Acesso Negado: Você não tem as permissões necessárias para acessar este recurso');
        });
    });
});
