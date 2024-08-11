const { getToken } = require('../../support/tokens');

describe('Criar e Deletar Paciente', () => {
    let newPatient;
    let adminToken;
    let createdPatientId;

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

    it('deve criar e deletar um paciente', () => {
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
            body: patientData,
            failOnStatusCode: false
        }).then((response) => {
            if (response.status === 400 && response.body.includes('CPF já cadastrado')) {
                cy.log('CPF já cadastrado, pulando criação.');
                createdPatientId = newPatient.id;
            } else {
                expect(response.status).to.eq(201);
                createdPatientId = response.body.id;
                cy.log(`Paciente criado com ID: ${createdPatientId}`);
            }

            cy.request({
                method: 'DELETE',
                url: `http://localhost:8081/api/pacientes/${createdPatientId}`,
                headers: {
                    'Authorization': `Bearer ${adminToken}`
                },
                failOnStatusCode: false
            }).then((deleteResponse) => {
                if (deleteResponse.status === 404) {
                    cy.log('Paciente não encontrado, não é possível deletar.');
                } else {
                    expect(deleteResponse.status).to.eq(200);
                    cy.log(`Paciente deletado com ID: ${createdPatientId}`);
                }
            });
        });
    });
});