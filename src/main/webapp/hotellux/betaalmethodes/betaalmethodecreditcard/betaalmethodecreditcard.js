document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("creditcardForm");
    const idInput = document.getElementById("betaalmethode_id");
    const naamInput = document.getElementById("volledigeNaam");
    const kaartInput = document.getElementById("kaartnummer");
    const vervalInput = document.getElementById("vervaldatum");
    const cvvInput = document.getElementById("cvv");

    const idError = document.getElementById("idError");
    const naamError = document.getElementById("naamError");
    const kaartError = document.getElementById("kaartError");
    const vervalError = document.getElementById("vervalError");
    const cvvError = document.getElementById("cvvError");
    const resultMessage = document.getElementById("resultMessage");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        [idError, naamError, kaartError, vervalError, cvvError, resultMessage].forEach(err => err.textContent = "");

        const id = idInput.value.trim();
        const naam = naamInput.value.trim();
        const kaart = kaartInput.value.trim();
        const vervaldatum = vervalInput.value;
        const cvv = cvvInput.value.trim();

        let valid = true;
        if (!id || isNaN(id) || parseInt(id) <= 0) { idError.textContent = "Ongeldig ID."; valid = false; }
        if (!naam || naam.length < 2) { naamError.textContent = "Naam is te kort."; valid = false; }
        if (!kaart || kaart.length < 12) { kaartError.textContent = "Kaartnummer te kort."; valid = false; }
        if (!vervaldatum) { vervalError.textContent = "Vervaldatum ontbreekt."; valid = false; }
        if (!cvv || cvv.length < 3 || cvv.length > 4) { cvvError.textContent = "CVV ongeldig."; valid = false; }
        if (!valid) return;

        try {
            const exists = await fetch(`http://localhost:8080/api/betaalmethodes/${id}`);
            if (!exists.ok) {
                idError.textContent = "Betaalmethode-ID bestaat niet.";
                return;
            }

            const payload = {
                id: parseInt(id),
                volledigeNaam: naam,
                kaartnummer: kaart,
                vervaldatum: vervaldatum,
                cvv: cvv
            };

            const res = await fetch(`http://localhost:8080/api/betaalmethodecreditcards/create`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            if (res.ok) {
                resultMessage.textContent = "Creditcard succesvol toegevoegd.";
                resultMessage.style.color = "green";
                form.reset();
            } else {
                resultMessage.textContent = "Fout bij toevoegen.";
                resultMessage.style.color = "red";
            }
        } catch (err) {
            console.error(err);
            resultMessage.textContent = "Netwerkfout of server offline.";
            resultMessage.style.color = "red";
        }
    });

    // voor die GET ALL
    const fetchCreditcardButton = document.getElementById('fetchCreditcardButton');
    const creditcardPopup = document.getElementById('creditcardPopup');
    const closeCreditcardPopup = document.getElementById('closeCreditcardPopup');
    const creditcardData = document.getElementById('creditcardData');

    fetchCreditcardButton.addEventListener('click', async () => {
        try {
            const response = await fetch('http://localhost:8080/api/betaalmethodecreditcards');

            if (!response.ok) {
                throw new Error(`HTTP-fout! Status: ${response.status}`);
            }

            const creditcards = await response.json();

            if (Array.isArray(creditcards) && creditcards.length > 0) {
                let output = '';
                creditcards.forEach(c => {
                    output += `
                    <strong>Creditcard ID:</strong> ${c.id ?? 'Onbekend'}<br>
                    <strong>Volledige Naam:</strong> ${c.volledigeNaam ?? 'Onbekend'}<br>
                    <strong>Kaartnummer:</strong> ${c.kaartnummer ?? 'Onbekend'}<br>
                    <strong>Vervaldatum:</strong> ${c.vervaldatum ?? 'Onbekend'}<br>
                    <strong>CVV:</strong> ${c.cvv ?? 'Onbekend'}<br>
                    <strong>Betaalmethode ID:</strong> ${c.betaalmethode?.id ?? 'Geen'}<br><br>
                    `;
                });
                creditcardData.innerHTML = output;
            } else {
                creditcardData.textContent = 'Geen creditcard betaalmethodes gevonden.';
            }

            creditcardPopup.classList.remove('hidden');
        } catch (error) {
            console.error('Fout bij ophalen van creditcard betaalmethodes:', error);
            creditcardData.textContent = 'Fout bij ophalen van creditcard betaalmethodes.';
            creditcardPopup.classList.remove('hidden');
        }
    });

    closeCreditcardPopup.addEventListener('click', () => {
        creditcardPopup.classList.add('hidden');
    });

    // GET BY ID voor Betaalmethode Creditcard
    const creditcardForm = document.getElementById('getBetaalmethodeCreditcardForm');
    const creditcardResultBox = document.getElementById('creditcardInfo');
    const creditcardDetails = document.getElementById('creditcardDetails');

    creditcardForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const idInput = document.getElementById('creditcard-id');
        const id = idInput.value.trim();

        if (!id || isNaN(id) || parseInt(id) <= 0) {
            creditcardDetails.innerHTML = "Voer een geldig positief getal in als ID.";
            creditcardResultBox.classList.remove('hidden');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/betaalmethodecreditcards/${id}`);

            if (!response.ok) {
                creditcardDetails.innerHTML = `Creditcard betaalmethode met ID <b>${id}</b> niet gevonden.`;
                creditcardResultBox.classList.remove('hidden');
                return;
            }

            const creditcardMethode = await response.json();

            // Defensieve checks op de ontvangen data
            if (!creditcardMethode || !creditcardMethode.id || !creditcardMethode.volledigeNaam || !creditcardMethode.kaartnummer || !creditcardMethode.vervaldatum || !creditcardMethode.cvv) {
                creditcardDetails.innerHTML = "Ongeldige data ontvangen.";
                creditcardResultBox.classList.remove('hidden');
                return;
            }

            creditcardDetails.innerHTML = `
            <b>ID:</b> ${creditcardMethode.id}<br>
            <b>Volledige naam:</b> ${creditcardMethode.volledigeNaam}<br>
            <b>Kaartnummer:</b> ${creditcardMethode.kaartnummer}<br>
            <b>Vervaldatum:</b> ${new Date(creditcardMethode.vervaldatum).toLocaleDateString()}<br>
            <b>CVV:</b> ${creditcardMethode.cvv}
        `;
            creditcardResultBox.classList.remove('hidden');
        } catch (error) {
            console.error('Fout bij ophalen creditcard betaalmethode:', error);
            creditcardDetails.innerHTML = "Er is een fout opgetreden bij het ophalen.";
            creditcardResultBox.classList.remove('hidden');
        }
    });

    // PUTT
    document.getElementById('updateBetaalmethodeCreditcardForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const idField = document.getElementById('creditcard-idd');
        const naamField = document.getElementById('volledige-naam');
        const kaartnummerField = document.getElementById('kaartnummerr');
        const vervaldatumField = document.getElementById('vervaldatumm');
        const cvvField = document.getElementById('cvvv');
        const resultBox = document.getElementById('updateResultCreditcard');

        const id = idField.value.trim();
        const volledigeNaam = naamField.value.trim();
        const kaartnummer = kaartnummerField.value.trim();
        const vervaldatum = vervaldatumField.value.trim();
        const cvv = cvvField.value.trim();

        if (!id || isNaN(id) || parseInt(id) <= 0) {
            resultBox.innerHTML = "Geef een geldig ID (positief getal).";
            resultBox.classList.remove('hidden');
            return;
        }
        if (!volledigeNaam) {
            resultBox.innerHTML = "Voer een geldige volledige naam in.";
            resultBox.classList.remove('hidden');
            return;
        }
        if (!kaartnummer) {
            resultBox.innerHTML = "Voer een geldig kaartnummer in.";
            resultBox.classList.remove('hidden');
            return;
        }
        if (!vervaldatum) {
            resultBox.innerHTML = "Voer een geldige vervaldatum in.";
            resultBox.classList.remove('hidden');
            return;
        }
        if (!cvv) {
            resultBox.innerHTML = "Voer een geldige CVV in.";
            resultBox.classList.remove('hidden');
            return;
        }

        try {
            const checkResponse = await fetch(`http://localhost:8080/api/betaalmethodecreditcards/${id}`);
            if (!checkResponse.ok) {
                resultBox.innerHTML = `Betaalmethode Creditcard met ID <b>${id}</b> niet gevonden.`;
                resultBox.classList.remove('hidden');
                return;
            }

            const updatedData = {
                id: parseInt(id),
                volledigeNaam: volledigeNaam,
                kaartnummer: kaartnummer,
                vervaldatum: vervaldatum,
                cvv: cvv
            };

            const response = await fetch(`http://localhost:8080/api/betaalmethodecreditcards/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedData)
            });

            if (!response.ok) {
                resultBox.innerHTML = "Fout bij bijwerken. Probeer opnieuw.";
                resultBox.classList.remove('hidden');
                return;
            }

            const result = await response.json();
            resultBox.innerHTML = `Betaalmethode Creditcard met ID <b>${result.id}</b> is succesvol bijgewerkt naar:<br>Naam: <b>${result.volledigeNaam}</b><br>Kaartnummer: <b>${result.kaartnummer}</b><br>Vervaldatum: <b>${result.vervaldatum}</b><br>CVV: <b>${result.cvv}</b>.`;
            resultBox.classList.remove('hidden');
        } catch (error) {
            console.error("Fout:", error);
            resultBox.innerHTML = "Er is een onverwachte fout opgetreden.";
            resultBox.classList.remove('hidden');
        }
    });
    //
    // DELETE
    const deleteCreditcardForm = document.getElementById('deleteBetaalmethodeCreditcardForm');
    const creditcardIdInput = document.getElementById('creditcard-idp');
    const deleteCreditcardMessage = document.getElementById('deleteCreditcardMessage');

    if (deleteCreditcardForm) {
        deleteCreditcardForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            if (creditcardIdInput) {
                const id = creditcardIdInput.value.trim();

                if (id && !isNaN(id) && parseInt(id) > 0) {
                    try {
                        const response = await fetch(`http://localhost:8080/api/betaalmethodecreditcards/${id}`, {
                            method: 'DELETE'
                        });

                        if (deleteCreditcardMessage) {
                            deleteCreditcardMessage.classList.remove('hidden');

                            if (response.status === 204) {
                                deleteCreditcardMessage.textContent = `BetaalmethodeCreditcard met ID ${id} succesvol verwijderd.`;
                                deleteCreditcardMessage.style.color = 'limegreen';
                            }

                            if (response.status === 404) {
                                deleteCreditcardMessage.textContent = `BetaalmethodeCreditcard met ID ${id} niet gevonden.`;
                                deleteCreditcardMessage.style.color = 'orange';
                            }

                            if (response.status !== 204 && response.status !== 404) {
                                deleteCreditcardMessage.textContent = "Fout bij verwijderen. Probeer opnieuw.";
                                deleteCreditcardMessage.style.color = 'red';
                            }
                        }
                    } catch (error) {
                        if (deleteCreditcardMessage) {
                            deleteCreditcardMessage.classList.remove('hidden');
                            deleteCreditcardMessage.textContent = "Netwerkfout of server onbereikbaar.";
                            deleteCreditcardMessage.style.color = 'red';
                        }
                        console.error('Fout bij verwijderen:', error);
                    }
                }

                if (!id || isNaN(id) || parseInt(id) < 1) {
                    if (deleteCreditcardMessage) {
                        deleteCreditcardMessage.classList.remove('hidden');
                        deleteCreditcardMessage.textContent = "Geef een geldig ID op.";
                        deleteCreditcardMessage.style.color = 'orangered';
                    }
                }
            }
        });
    }


});
