document.addEventListener('DOMContentLoaded', () => {

    // validatie

    function validateForm(fields) {
        let isValid = true;
        fields.forEach(field => {
            const input = document.getElementById(field);
            const msg = document.getElementById(`${field}-validation`);
            if (!input.value.trim()) {
                msg.style.display = 'block';
                isValid = false;
            } else {
                msg.style.display = 'none';
            }
        });
        return isValid;
    }

    async function checkKamerBestaat(id) {
        try {
            const response = await fetch(`http://localhost:8080/api/kamers/${id}`);
            return response.ok;
        } catch (error) {
            console.error('Fout bij het checken van kamer:', error);
            return false;
        }
    }


    // POST
    document.getElementById('beschikbareKamerForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const velden = ['beschikbarekameralternatief', 'kamer_id'];
        if (!validateForm(velden)) return;

        const kamerId = document.getElementById('kamer_id').value.trim();
        const responseBox = document.getElementById('beschikbareKamerResponse');

        // Check of kamer bestaat
        const bestaat = await checkKamerBestaat(kamerId);
        if (!bestaat) {
            responseBox.textContent = `Kamer ID ${kamerId} bestaat niet. Kies een geldig ID.`;
            responseBox.style.color = 'crimson';
            return;
        }

        const data = {
            beschikbareKamerAlternatief: document.getElementById('beschikbarekameralternatief').value,
            kamer: {
                id: parseInt(kamerId)
            }
        };

        try {
            const response = await fetch('http://localhost:8080/api/beschikbarekamers/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            if (!response.ok) throw new Error('Fout bij toevoegen.');

            const result = await response.json();
            responseBox.textContent =
                `Beschikbare kamer aangemaakt met ID: ${result.id}, status: ${result.beschikbareKamerAlternatief}`;
            responseBox.style.color = 'limegreen';

            event.target.reset();
        } catch (err) {
            console.error("Fout bij POST:", err);
            responseBox.textContent = "Er ging iets mis bij het aanmaken van de beschikbare kamer.";
            responseBox.style.color = 'crimson';
        }
    });

    // GET ALL

    const fetchBeschikbareButton = document.getElementById('fetchBeschikbareKamersButton');
    const beschikbarePopup = document.getElementById('beschikbareKamersPopup');
    const closePopup = document.getElementById('closeBeschikbareKamersPopup');
    const beschikbareDiv = document.getElementById('beschikbareKamers');

    fetchBeschikbareButton.addEventListener('click', async () => {
        try {
            const response = await fetch('http://localhost:8080/api/beschikbarekamers');

            if (!response.ok) {
                throw new Error(`Server responded with ${response.status}`);
            }

            const kamers = await response.json();

            if (!kamers || kamers.length === 0) {
                beschikbareDiv.innerHTML = "Geen beschikbare kamers gevonden.";
            } else {
                let output = '';
                kamers.forEach(k => {
                    output += `
                        <strong>ID:</strong> ${k.id}<br>
                        <strong>Status:</strong> ${k.beschikbareKamerAlternatief}<br>
                        <strong>Kamer ID:</strong> ${k.kamer ? k.kamer.id : 'Onbekend'}<br>
                        <strong>Kamertype:</strong> ${k.kamer ? k.kamer.kamertype : 'N.V.T.'}<br>
                        <strong>Prijs:</strong> $${k.kamer ? k.kamer.prijsPerMaand : 'N.V.T.'}<br><br>
                    `;
                });
                beschikbareDiv.innerHTML = output;
            }

            beschikbarePopup.classList.remove('hidden');
        } catch (error) {
            console.error('Fout bij ophalen beschikbare kamers:', error);
            beschikbareDiv.textContent = "Fout bij het ophalen van beschikbare kamers.";
            beschikbarePopup.classList.remove('hidden');
        }
    });

    closePopup.addEventListener('click', () => {
        beschikbarePopup.classList.add('hidden');
    });

    // dit is voor die GET ID
    const btn = document.getElementById('getBeschikbareKamerByIdBtn');
    const popup = document.getElementById('beschikbareKamerByIdPopup');
    const closeBtn = document.getElementById('closeBeschikbareKamerPopup');
    const form = document.getElementById('beschikbareKamerByIdForm');
    const idInput = document.getElementById('beschikbareKamerIdInput');
    const result = document.getElementById('beschikbareKamerByIdResult');

    btn.addEventListener('click', () => {
        popup.classList.remove('hidden');
    });

    closeBtn.addEventListener('click', () => {
        popup.classList.add('hidden');
        result.textContent = '';
        idInput.value = '';
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = idInput.value.trim();

        if (!id || isNaN(id) || parseInt(id) <= 0) {
            result.textContent = "Geef een geldig ID in (groter dan 0).";
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/beschikbarekamers/${id}`);

            if (!response.ok) {
                if (response.status === 404) {
                    result.innerHTML = `Geen beschikbare kamer gevonden met ID <strong>${id}</strong>.`;
                } else {
                    result.textContent = `Fout ${response.status}: ${response.statusText}`;
                }
                return;
            }

            const kamer = await response.json();
            result.innerHTML = `
                <strong>ID:</strong> ${kamer.id}<br>
                <strong>Status:</strong> ${kamer.beschikbareKamerAlternatief}<br>
                <strong>Kamer ID:</strong> ${kamer.kamer.id}<br>
                <strong>Type:</strong> ${kamer.kamer.kamertype}<br>
                <strong>Bedden:</strong> ${kamer.kamer.aantalbedden}<br>
                <strong>Prijs per maand:</strong> €${parseFloat(kamer.kamer.prijsPerMaand).toFixed(2)}
            `;
        } catch (err) {
            console.error("Fout bij ophalen:", err);
            result.textContent = "Er is een fout opgetreden bij het ophalen van de beschikbare kamer.";
        }
    });

    // PUTT
    const kamerId = document.getElementById('update-kamer-id').value.trim();
    const updateForm = document.getElementById('updateBeschikbareKamerForm');
    const rresult = document.getElementById('updateBeschikbareKamerResult');

    updateForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const id = document.getElementById('update-bk-id').value.trim();
        const status = document.getElementById('update-beschikbaarheid').value.trim();
        const kamerId = document.getElementById('update-kamer-id').value.trim();

        if (!id || !status || !kamerId) {
            rresult.textContent = "Alle velden zijn verplicht.";
            return;
        }

        // Eerst checken of kamer-ID bestaat
        try {
            const kamerResponse = await fetch(`http://localhost:8080/api/kamers/${kamerId}`);
            if (!kamerResponse.ok) {
                rresult.textContent = `Kamer met ID ${kamerId} bestaat niet.`;
                return;
            }
        } catch (err) {
            rresult.textContent = "Fout bij het controleren van de kamer-ID.";
            return;
        }

        const data = {
            id: parseInt(id),
            beschikbareKamerAlternatief: status,
            kamer: { id: parseInt(kamerId) }
        };

        try {
            const response = await fetch(`http://localhost:8080/api/beschikbarekamers/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                if (response.status === 404) {
                    rresult.textContent = `Geen beschikbare kamer gevonden met ID ${id}.`;
                } else {
                    rresult.textContent = `Fout ${response.status}: ${response.statusText}`;
                }
                return;
            }

            const res = await response.json();
            rresult.innerHTML = `
                <b>Beschikbare Kamer bijgewerkt:</b><br>
                ID: ${res.id}<br>
                Status: ${res.beschikbareKamerAlternatief}<br>
                Kamer ID: ${res.kamer?.id ?? 'Onbekend'}<br>
                Type: ${res.kamer?.kamertype ?? 'Onbekend'}
            `;
        } catch (err) {
            console.error("Update mislukt:", err);
            result.textContent = "Er is een fout opgetreden bij het updaten.";
        }
    });

    // DELETE
    const deleteForm = document.getElementById('deleteBkForm');
    const message = document.getElementById('deleteBkMessage');

    deleteForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = document.getElementById('bk-id').value.trim();

        if (!id) {
            message.textContent = "Voer een geldig ID in.";
            message.style.color = "orange";
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/beschikbarekamers/${id}`, {
                method: 'DELETE',
            });

            if (response.status === 204) {
                message.textContent = `Beschikbare kamer met ID ${id} succesvol verwijderd.`;
                message.style.color = "limegreen";
            } else if (response.status === 404) {
                message.textContent = `Beschikbare kamer met ID ${id} bestaat niet.`;
                message.style.color = "orangered";
            } else {
                message.textContent = `Fout ${response.status}: Kon niet verwijderen.`;
                message.style.color = "red";
            }
        } catch (err) {
            console.error("Fout tijdens verwijderen:", err);
            message.textContent = "Verbindingsfout. Probeer opnieuw.";
            message.style.color = "red";
        }
    });



});
