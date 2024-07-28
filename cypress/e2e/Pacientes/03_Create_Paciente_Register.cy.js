const { getToken } = require('../../support/tokens');

describe('Create Patient', () => {
    let newPatient;
    let adminToken;

    before(() => {
        cy.task('getPatientData').then((patient) => {
            newPatient = patient;
            cy.log(`Retrieved patient data: ${JSON.stringify(newPatient)}`);
            expect(newPatient).to.not.be.undefined;
        });

        cy.task('getToken', 'ADMIN').then((token) => {
            adminToken = token;
            expect(adminToken).to.not.be.undefined;
        });
    });

    it('should create a new patient using stored data', () => {
        const formattedCpf = `${newPatient.cpf.slice(0, 3)}.${newPatient.cpf.slice(3, 6)}.${newPatient.cpf.slice(6, 9)}-${newPatient.cpf.slice(9, 11)}`;

        const patientData = {
            nomeCompleto: newPatient.nome,
            genero: "Masculino",
            dataNascimento: newPatient.dataNascimento,
            cpf: formattedCpf,
            rg: "SC-12.345.678",
            orgaoExpedidorRg: "SSP",
            estadoCivil: "Solteiro",
            telefone: "(48) 9 9663-6469",
            email: newPatient.email,
            naturalidade: "São José",
            contatoEmergencia: "(48) 9 9838-3989",
            listaAlergias: ["Pólen", "Amendoim"],
            listaCuidadosEspecificos: ["Usar protetor solar diariamente"],
            convenio: "Saúde Total",
            numeroConvenio: "123456789",
            validadeConvenio: "2025-12-31",
            usuario: {
                id: newPatient.id
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
            body: patientData
        }).then((response) => {
            expect(response.status).to.eq(201);
            const createdPatientId = response.body.id;
            cy.task('savePatientId', createdPatientId).then(() => {
                cy.log(`Patient created with ID: ${createdPatientId}`);
            });
        });
    });
});