document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("contantForm");
    const idInput = document.getElementById("betaalmethode_id");
    const valutaInput = document.getElementById("valuta");
    const errorId = document.getElementById("betaalError");
    const errorValuta = document.getElementById("valutaError");
    const resultMessage = document.getElementById("resultMessage");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const id = idInput.value.trim();
        const valuta = valutaInput.value.trim().toUpperCase();

        errorId.textContent = "";
        errorValuta.textContent = "";
        resultMessage.textContent = "";

        let valid = true;

        if (!id || isNaN(id) || parseInt(id) <= 0) {
            errorId.textContent = "Geef een geldige ID op.";
            valid = false;
        }

        if (!valuta || valuta.length < 2) {
            errorValuta.textContent = "Geef een geldige valuta op.";
            valid = false;
        }

        if (!valid) return;

        const exists = await fetch(`http://localhost:8080/api/betaalmethodes/${id}`);
        if (!exists.ok) {
            errorId.textContent = "Betaalmethode-ID bestaat niet.";
            return;
        }

        const contantPayload = {
            id: parseInt(id),
            valuta: valuta
        };

        try {
            const res = await fetch(`http://localhost:8080/api/betaalmethodecontanten/create`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(contantPayload)
            });

            if (res.ok) {
                resultMessage.textContent = "Contante betaalmethode succesvol toegevoegd.";
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

    // // GET ALL
    //
    // const fetchButton = document.getElementById('fetchBetaalmethodeContantButton');
    // const popup = document.getElementById('betaalmethodeContantPopup');
    // const closeButton = document.getElementById('closeBetaalmethodeContantPopup');
    // const resultDiv = document.getElementById('betaalmethodeContantResult');
    //
    // fetchButton.addEventListener('click', async () => {
    //     try {
    //         const response = await fetch('http://localhost:8080/api/betaalmethodecontant');
    //
    //         if (!response.ok) {
    //             throw new Error('Server gaf een fout terug.');
    //         }
    //
    //         const betaalmethodeContanten = await response.json();
    //
    //         if (Array.isArray(betaalmethodeContanten) && betaalmethodeContanten.length > 0) {
    //             let output = '';
    //             betaalmethodeContanten.forEach(bc => {
    //                 output += `
    //                 ID: ${bc.id}
    //                 Valuta: ${bc.valuta}
    //                 Betaalmethode ID: ${bc.betaalmethode ? bc.betaalmethode.id : 'Geen'}
    //
    //                 -------------------------------
    //                 `;
    //             });
    //
    //             // Methode: ${bc.betaalmethode && bc.betaalmethode.methode ? bc.betaalmethode.methode : 'Onbekend'}
    //             // Datum: ${bc.betaalmethode && bc.betaalmethode.datum ? bc.betaalmethode.datum : 'Onbekend'}
    //             // Klant ID: ${bc.betaalmethode && bc.betaalmethode.klant ? bc.betaalmethode.klant.id : 'Onbekend'}
    //             resultDiv.textContent = output;
    //         } else {
    //             resultDiv.textContent = "Geen BetaalmethodeContant records gevonden.";
    //         }
    //
    //         popup.classList.remove('hidden');
    //
    //     } catch (error) {
    //         console.error('Fout bij ophalen BetaalmethodeContant:', error);
    //         resultDiv.textContent = "Fout bij het ophalen van BetaalmethodeContant records.";
    //         popup.classList.remove('hidden');
    //     }
    // });
    //
    // closeButton.addEventListener('click', () => {
    //     popup.classList.add('hidden');
    // });

    // // // GET ALL
    //
    // const fetchButton = document.getElementById('fetchBetaalmethodeContantButton');
    // const popup = document.getElementById('betaalmethodeContantPopup');
    // const closeButton = document.getElementById('closeBetaalmethodeContantPopup');
    // const resultDiv = document.getElementById('betaalmethodeContantResult');
    //
    // fetchButton.addEventListener('click', async () => {
    //     try {
    //         const response = await fetch('http://localhost:8080/api/betaalmethodecontant');
    //
    //         if (!response.ok) {
    //             throw new Error('Server gaf een fout terug.');
    //         }
    //
    //         const betaalmethodeContanten = await response.json();
    //
    //         if (Array.isArray(betaalmethodeContanten) && betaalmethodeContanten.length > 0) {
    //             let output = '';
    //             betaalmethodeContanten.forEach(bc => {
    //                 let datum = 'Onbekend';
    //                 if (bc.betaalmethode && typeof bc.betaalmethode.datum === 'number') {
    //                     const dateObj = new Date(bc.betaalmethode.datum);
    //                     datum = dateObj.toLocaleDateString(); // Mooie leesbare datum
    //                 }
    //
    //                 output += `
    //                     ID: ${bc.id}
    //                     Valuta: ${bc.valuta}
    //                     Betaalmethode ID: ${bc.betaalmethode ? bc.betaalmethode.id : 'Geen'}
    //                     Methode: ${bc.betaalmethode && bc.betaalmethode.methode ? bc.betaalmethode.methode : 'Onbekend'}
    //                     Datum: ${datum}
    //                     Klant ID: ${bc.betaalmethode && bc.betaalmethode.klant ? bc.betaalmethode.klant.id : 'Onbekend'}
    //
    //                     -------------------------------
    //                     `;
    //             });
    //             resultDiv.textContent = output;
    //         } else {
    //             resultDiv.textContent = "Geen BetaalmethodeContant records gevonden.";
    //         }
    //
    //         popup.classList.remove('hidden');
    //
    //     } catch (error) {
    //         console.error('Fout bij ophalen BetaalmethodeContant:', error);
    //         resultDiv.textContent = "Fout bij het ophalen van BetaalmethodeContant records.";
    //         popup.classList.remove('hidden');
    //     }
    // });

    // GET ALL

    const fetchButton = document.getElementById('fetchBetaalmethodeContantButton');
    const popup = document.getElementById('betaalmethodeContantPopup');
    const closeButton = document.getElementById('closeBetaalmethodeContantPopup');
    const resultDiv = document.getElementById('betaalmethodeContantResult');

    fetchButton.addEventListener('click', async () => {
        try {
            const response = await fetch('http://localhost:8080/api/betaalmethodecontanten');
            const betaalmethodeContanten = await response.json();

            if (!response.ok) {
                throw new Error('Server gaf een fout terug.');
            }

            // Debugging: log de response om te zien wat er binnenkomt
            console.log(betaalmethodeContanten);

            if (Array.isArray(betaalmethodeContanten) && betaalmethodeContanten.length > 0) {
                let output = '';
                betaalmethodeContanten.forEach(bc => {
                    output += `
                    <strong>ID:</strong> ${bc.betaalmethode ? bc.betaalmethode.id : 'Geen ID'}<br>
                    <strong>Valuta:</strong> ${bc.valuta}<br><br>
                `;
                });
                resultDiv.innerHTML = output;  // HIER innerHTML ipv textContent
            } else {
                resultDiv.innerHTML = "Geen BetaalmethodeContant records gevonden.";
            }

            popup.classList.remove('hidden');
        } catch (error) {
            console.error('Fout bij ophalen BetaalmethodeContant:', error);
            resultDiv.innerHTML = "Fout bij het ophalen van BetaalmethodeContant records.";
            popup.classList.remove('hidden');
        }
    });

    // GET BY ID

    const fform = document.getElementById('getBetaalmethodeContantForm');
    const resultBox = document.getElementById('contantInfo');
    const details = document.getElementById('contantDetails');

    fform.addEventListener('submit', async (event) => {
        event.preventDefault();

        const idInput = document.getElementById('contant-id');
        const id = idInput.value.trim();

        if (!id || isNaN(id) || parseInt(id) <= 0) {
            details.innerHTML = "Voer een geldig positief getal in als ID.";
            resultBox.classList.remove('hidden');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/betaalmethodecontanten/${id}`);

            if (!response.ok) {
                details.innerHTML = `Contante betaalmethode met ID <b>${id}</b> niet gevonden.`;
                resultBox.classList.remove('hidden');
                return;
            }

            const contantMethode = await response.json();

            if (!contantMethode || !contantMethode.id || !contantMethode.valuta) {
                details.innerHTML = "Ongeldige data ontvangen.";
                resultBox.classList.remove('hidden');
                return;
            }

            details.innerHTML = `
        <b>ID:</b> ${contantMethode.id}<br>
        <b>Valuta:</b> ${contantMethode.valuta}
      `;
            resultBox.classList.remove('hidden');
        } catch (error) {
            console.error('Fout bij ophalen contante betaalmethode:', error);
            details.innerHTML = "Er is een fout opgetreden bij het ophalen.";
            resultBox.classList.remove('hidden');
        }
    });

    // UPDATE BETAALMETHODECONTANT
    document.getElementById('updateBetaalmethodeContantForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const idField = document.getElementById('contant-idd');
        const valutaField = document.getElementById('contant-valuta');
        const resultBox = document.getElementById('updateResultContant');

        const id = idField.value.trim();
        const valuta = valutaField.value.trim();

        if (!id || isNaN(id) || parseInt(id) <= 0) {
            resultBox.innerHTML = "Geef een geldig ID (positief getal).";
            resultBox.classList.remove('hidden');
            return;
        }

        if (!valuta) {
            resultBox.innerHTML = "Geef een geldige valuta op.";
            resultBox.classList.remove('hidden');
            return;
        }

        try {
            // Controleren of de bestaande betaalmethodecontant bestaat
            const checkResponse = await fetch(`http://localhost:8080/api/betaalmethodecontanten/${id}`);
            if (!checkResponse.ok) {
                resultBox.innerHTML = `Betaalmethode Contant met ID <b>${id}</b> niet gevonden.`;
                resultBox.classList.remove('hidden');
                return;
            }

            const updatedData = {
                id: parseInt(id),
                valuta: valuta
            };

            const response = await fetch(`http://localhost:8080/api/betaalmethodecontanten/${id}`, {
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
            resultBox.innerHTML = `Betaalmethode Contant met ID <b>${result.id}</b> is succesvol bijgewerkt naar valuta: <b>${result.valuta}</b>.`;
            resultBox.classList.remove('hidden');
        } catch (error) {
            console.error("Fout:", error);
            resultBox.innerHTML = "Er is een onverwachte fout opgetreden.";
            resultBox.classList.remove('hidden');
        }
    });

    // voor die DELETE
    const deleteForm = document.getElementById('deleteBetaalmethodeContantForm');
    const iidInput = document.getElementById('contant-idp');
    const message = document.getElementById('deleteContantMessage');

    deleteForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = iidInput.value.trim();

        if (!id || isNaN(id) || parseInt(id) < 1) {
            message.textContent = "Geef een geldig ID op.";
            message.style.color = "orangered";
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/betaalmethodecontanten/${id}`, {
                method: 'DELETE'
            });

            if (response.status === 204) {
                message.textContent = `Contante betaalmethode met ID ${id} succesvol verwijderd.`;
                message.style.color = "limegreen";
            } else if (response.status === 404) {
                message.textContent = `BetaalmethodeContant met ID ${id} niet gevonden.`;
                message.style.color = "orange";
            } else {
                message.textContent = "Fout bij verwijderen. Probeer opnieuw.";
                message.style.color = "red";
            }
        } catch (error) {
            console.error("Fout bij verwijderen:", error);
            message.textContent = "Netwerkfout of server onbereikbaar.";
            message.style.color = "red";
        }
    });




});
