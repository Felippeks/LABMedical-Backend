const { getToken } = require('../../support/tokens');

describe('Obter Token', () => {
    let newPatient;
    let adminToken;

    before(() => {
        cy.task('getPatientData').then((patient) => {
            newPatient = patient;
            cy.log(`Dados do paciente recuperados: ${JSON.stringify(newPatient)}`);
            expect(newPatient).to.not.be.undefined;
        });

        cy.task('getToken', 'ADMIN').then((token) => {
            adminToken = token;
            expect(adminToken).to.not.be.undefined;
        });
    });

    it('deve tentar criar um novo paciente usando os dados ausentes', () => {
        const patientData = {
            nomeCompleto: "",
            genero: "Masculino",
            dataNascimento: "",
            cpf: "",
            rg: "SC-12.345.678",
            orgaoExpedidorRg: "SSP",
            estadoCivil: "Solteiro",
            telefone: "(48) 9 9663-6469",
            email: "",
            naturalidade: "São José",
            contatoEmergencia: "(48) 9 9838-3989",
            listaAlergias: ["Pólen", "Amendoim"],
            listaCuidadosEspecificos: ["Usar protetor solar diariamente"],
            convenio: "Saúde Total",
            numeroConvenio: "123456789",
            validadeConvenio: "2025-12-31",
            usuario: {
                id: "2"
            },
            endereco: {
                logradouro: "Rua das Flores",
                numero: "123",
                complemento: "Apto 101",
                bairro: "Jardim das Árvores",
                cidade: "Belo Horizonte",
                estado: "MG",
                cep: "31270-010"
            }
        };

        cy.request({
            method: 'POST',
            url: 'http://localhost:8081/api/pacientes',
            headers: {
                'Authorization': `Bearer ${adminToken}`
            },
            body: patientData,
            failOnStatusCode: false
        }).then((response) => {
            expect(response.status).to.eq(400);
            cy.log(`Erro ao criar paciente: ${response.body.message}`);
        });
    });
});