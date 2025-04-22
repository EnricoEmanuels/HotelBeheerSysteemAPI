document.addEventListener('DOMContentLoaded', () => {
    const betaalmethodeForm = document.getElementById('betaalmethodeForm');
    const responseDiv = document.getElementById('createResponse');

    // === VALIDATIEFUNCTIE ===
    function validateForm(fields) {
        let isValid = true;
        fields.forEach(field => {
            const input = document.getElementById(field);
            const validationMessage = document.getElementById(`${field}-validation`);
            if (!input.value.trim()) {
                validationMessage.style.display = 'block';
                isValid = false;
            } else {
                validationMessage.style.display = 'none';
            }
        });
        return isValid;
    }

    // // === POST SUBMIT LOGICA ===
    // betaalmethodeForm.addEventListener('submit', async (event) => {
    //     event.preventDefault();
    //
    //     const isValid = validateForm(['methode', 'datum', 'klant_id']);
    //     if (!isValid) return;
    //
    //     const betaalmethode = {
    //         methode: document.getElementById('methode').value,
    //         datum: document.getElementById('datum').value,
    //         klant: {
    //             id: parseInt(document.getElementById('klant_id').value)
    //         }
    //     };
    //
    //     try {
    //         const response = await fetch('http://localhost:8080/api/betaalmethodes/create', {
    //             method: 'POST',
    //             headers: { 'Content-Type': 'application/json' },
    //             body: JSON.stringify(betaalmethode)
    //         });
    //
    //         if (!response.ok) {
    //             if (response.status === 500) {
    //                 responseDiv.innerHTML = "Deze klant ID bestaat nog niet. Kan niet opslaan.";
    //             } else {
    //                 responseDiv.innerHTML = `Er is iets misgegaan (status ${response.status}).`;
    //             }
    //             return;
    //         }
    //
    //         const result = await response.json();
    //
    //         responseDiv.innerHTML = `
    //             Betaalmethode succesvol aangemaakt:<br>
    //             <b>ID:</b> ${result.id}<br>
    //             <b>Methode:</b> ${result.methode}<br>
    //             <b>Datum:</b> ${result.datum}<br>
    //             <b>Klant ID:</b> ${result.klant.id}
    //         `;
    //         betaalmethodeForm.reset();
    //     } catch (error) {
    //         console.error('Fout bij aanmaken betaalmethode:', error);
    //         responseDiv.innerHTML = "Er is een onverwachte fout opgetreden. Probeer opnieuw.";
    //     }
    // });


    // === POST SUBMIT LOGICA ===
    betaalmethodeForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const isValid = validateForm(['methode', 'datum', 'klant_id']);
        if (!isValid) return;

        const klantId = parseInt(document.getElementById('klant_id').value);

        // === Check of klant-ID bestaat ===
        try {
            const klantResponse = await fetch(`http://localhost:8080/api/klanten/${klantId}`);

            if (!klantResponse.ok) {
                if (klantResponse.status === 404) {
                    responseDiv.innerHTML = `Klant met ID <strong>${klantId}</strong> bestaat niet. Kan niet opslaan.`;
                } else {
                    responseDiv.innerHTML = `Fout bij controleren klant: ${klantResponse.statusText}`;
                }
                return;
            }
        } catch (error) {
            console.error('Fout bij controleren klant:', error);
            responseDiv.innerHTML = "Kan klant-ID niet verifiëren. Probeer opnieuw.";
            return;
        }

        // === Als klant bestaat, ga door met POST ===
        const betaalmethode = {
            methode: document.getElementById('methode').value,
            datum: document.getElementById('datum').value,
            klant: { id: klantId }
        };

        try {
            const response = await fetch('http://localhost:8080/api/betaalmethodes/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(betaalmethode)
            });

            if (!response.ok) {
                if (response.status === 500) {
                    responseDiv.innerHTML = "Fout: kan niet opslaan. Controleer gegevens.";
                } else {
                    responseDiv.innerHTML = `Er is iets misgegaan (status ${response.status}).`;
                }
                return;
            }

            const result = await response.json();

            responseDiv.innerHTML = `
            Betaalmethode succesvol aangemaakt:<br>
            <b>ID:</b> ${result.id}<br>
            <b>Methode:</b> ${result.methode}<br>
            <b>Datum:</b> ${result.datum}<br>
            <b>Klant ID:</b> ${result.klant.id}
        `;
            betaalmethodeForm.reset();
        } catch (error) {
            console.error('Fout bij aanmaken betaalmethode:', error);
            responseDiv.innerHTML = "Er is een onverwachte fout opgetreden. Probeer opnieuw.";
        }
    });

    // get ALL

    const fetchBetaalmethodesButton = document.getElementById('fetchBetaalmethodesButton');
    const betaalmethodesPopup = document.getElementById('betaalmethodesPopup');
    const closeBetaalmethodesPopup = document.getElementById('closeBetaalmethodesPopup');
    const betaalmethodesDiv = document.getElementById('betaalmethodes');

    fetchBetaalmethodesButton.addEventListener('click', async () => {
        try {
            const response = await fetch('http://localhost:8080/api/betaalmethodes');
            const betaalmethodes = await response.json();

            if (betaalmethodes.length === 0) {
                betaalmethodesDiv.innerHTML = "Geen betaalmethodes gevonden.";
            } else {
                let output = '';
                betaalmethodes.forEach(b => {
                    output += `
                    <strong>ID:</strong> ${b.id}<br>
                    <strong>Methode:</strong> ${b.methode}<br>
                    <strong>Datum:</strong> ${b.datum}<br>
                    <strong>Klant ID:</strong> ${b.klant ? b.klant.id : 'Geen'}<br><br>
                `;
                });
                betaalmethodesDiv.innerHTML = output;
            }

            betaalmethodesPopup.classList.remove('hidden');

        } catch (error) {
            console.error('Fout bij ophalen betaalmethodes:', error);
            betaalmethodesDiv.textContent = "Fout bij het ophalen van betaalmethodes.";
            betaalmethodesPopup.classList.remove('hidden');
        }
    });

    closeBetaalmethodesPopup.addEventListener('click', () => {
        betaalmethodesPopup.classList.add('hidden');
    });

    // GET ID
    // document.getElementById('getBetaalmethodeForm').addEventListener('submit', async (event) => {
    //     event.preventDefault();
    //
    //     const id = document.getElementById('betaalmethode-id').value.trim();
    //     const resultBox = document.getElementById('betaalmethodeInfo');
    //     const details = document.getElementById('betaalmethodeDetails');
    //
    //     if (!id || isNaN(id) || parseInt(id) <= 0) {
    //         details.innerHTML = "Voer een geldig positief getal in als ID.";
    //         resultBox.classList.remove('hidden');
    //         return;
    //     }
    //
    //     try {
    //         const response = await fetch(`http://localhost:8080/api/betaalmethodes/${id}`);
    //
    //         if (!response.ok) {
    //             details.innerHTML = `Betaalmethode met ID <b>${id}</b> niet gevonden.`;
    //             resultBox.classList.remove('hidden');
    //             return;
    //         }
    //
    //         const methode = await response.json();
    //
    //         // Verander dit als je DTO andere namen heeft!
    //         details.innerHTML = `
    //         <b>ID:</b> ${methode.id}<br>
    //         <b>Methode:</b> ${methode.methode}<br>
    //         <b>Datum:</b> ${methode.datum}<br>
    //         <b>Klant ID:</b> ${methode.klant_id ?? 'Geen'}<br>
    //     `;
    //
    //         resultBox.classList.remove('hidden');
    //     } catch (error) {
    //         console.error('Fout bij het ophalen van betaalmethode:', error);
    //         details.innerHTML = "Er is een fout opgetreden bij het ophalen.";
    //         resultBox.classList.remove('hidden');
    //     }
    // });
    //

    document.getElementById('getBetaalmethodeForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const id = document.getElementById('betaalmethode-id').value.trim();
        const resultBox = document.getElementById('betaalmethodeInfo');
        const details = document.getElementById('betaalmethodeDetails');

        if (!id || isNaN(id) || parseInt(id) <= 0) {
            details.innerHTML = "Voer een geldig positief getal in als ID.";
            resultBox.classList.remove('hidden');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/betaalmethodes/${id}`);

            if (!response.ok) {
                details.innerHTML = `Betaalmethode met ID <b>${id}</b> niet gevonden.`;

                resultBox.classList.remove('hidden');
                return;
            }

            const methode = await response.json();

            // Datum netjes formatteren
            const date = new Date(methode.datum);
            const formattedDate = `${date.getMonth() + 1}-${date.getDate()}-${date.getFullYear()}` ;

            // Klant ID fixen
            const klantId = methode.klant?.id || methode.klant_id || 'Geen';

            details.innerHTML = `
            <b>ID:</b> ${methode.id}<br>
            <b>Methode:</b> ${methode.methode}<br>
            <b>Datum:</b> ${formattedDate}<br>
            <b>Klant ID:</b> ${klantId}
        `;

            resultBox.classList.remove('hidden');
        } catch (error) {
            console.error('Fout bij het ophalen van betaalmethode:', error);
            details.innerHTML = "Er is een fout opgetreden bij het ophalen.";
            resultBox.classList.remove('hidden');
        }
    });

    // PUTT
    document.getElementById('updateBetaalmethodeForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const id = document.getElementById('put-betaalmethode-id').value.trim();
        const methode = document.getElementById('put-methode').value.trim();
        const datum = document.getElementById('put-datum').value;
        const klantId = document.getElementById('put-klant-id').value.trim();

        if (!id || !methode || !datum || !klantId) {
            alert("Vul alle velden correct in.");
            return;
        }

        try {
            const klantResponse = await fetch(`http://localhost:8080/api/klanten/${klantId}`);
            if (!klantResponse.ok) {
                if (klantResponse.status === 404) {
                    document.getElementById('updateBetaalmethodeResponse').textContent = `Klant met ID ${klantId} bestaat niet.`;
                    return;
                } else {
                    throw new Error("Fout bij klant-validatie.");
                }
            }

            const updatedBetaalmethode = {
                id: parseInt(id),
                methode: methode,
                datum: datum,
                klant: {
                    id: parseInt(klantId)
                }
            };

            const response = await fetch(`http://localhost:8080/api/betaalmethodes/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedBetaalmethode)
            });

            if (!response.ok) throw new Error("Update mislukt.");

            document.getElementById('updateBetaalmethodeResponse').textContent = `Betaalmethode met ID ${id} is succesvol bijgewerkt.`;

        } catch (error) {
            console.error("Fout bij update:", error);
            document.getElementById('updateBetaalmethodeResponse').textContent = "Update mislukt. Controleer de console.";
        }
    });



});
