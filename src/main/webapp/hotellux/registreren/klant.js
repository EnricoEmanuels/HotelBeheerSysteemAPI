document.addEventListener('DOMContentLoaded', () => {
    // Validatiefunctie zoals bij je meester
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

    // Formulier afhandeling
    document.getElementById('klantForm').addEventListener('submit', (event) => {
        event.preventDefault();

        // Alleen verplichte velden valideren
        const isValid = validateForm([
            'voornaam',
            'achternaam',
            'telefoon',
            'email',
            'balans'
        ]);

        if (!isValid) return;

        // Hier komt straks de POST naar de API (volgende stap)
        alert("Formulier is geldig en klaar voor verzenden!");
    });

    // POST
    document.getElementById('klantForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const verplichteVelden = ['voornaam', 'achternaam', 'telefoon', 'email', 'balans'];
        if (!validateForm(verplichteVelden)) return;

        const formData = new FormData(event.target);
        const data = Object.fromEntries(formData.entries());

        // balans moet naar float, betaalmethode_id naar int (indien ingevuld)
        data.balans = parseFloat(data.balans);
        if (data.betaalmethode_id) {
            data.betaalmethode = { id: parseInt(data.betaalmethode_id) };
        }
        delete data.betaalmethode_id;

        try {
            const response = await fetch('http://localhost:8080/api/klanten/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            const result = await response.json();
            const responseDiv = document.getElementById('createResponse');

            responseDiv.innerHTML = `
              Klant succesvol aangemaakt:<br>
              <b>ID:</b> ${result.id}<br>
              <b>Voornaam:</b> ${result.voornaam}<br>
              <b>Achternaam:</b> ${result.achternaam}<br>
              <b>Telefoonnummer:</b> ${result.telefoon}<br>
              <b>Email:</b> ${result.email}<br>
              <b>Balans:</b> $ ${result.balans.toFixed(2)}<br>
              <b>Betaalmethode ID:</b> ${result.betaalmethode ? result.betaalmethode.id : 'Geen'}
`;
            // alert(`Klant aangemaakt met ID: ${result.id}`);
            event.target.reset();
        } catch (error) {
            console.error('Fout bij aanmaken klant:', error);
            alert("Er ging iets mis bij het registreren.");
        }
    });

    // GET ALL Klanten

    const fetchButton = document.getElementById('fetchKlantenButton');
    const klantenPopup = document.getElementById('klantenPopup');
    const closePopup = document.getElementById('closePopup');
    const klantenDiv = document.getElementById('klanten');

    fetchButton.addEventListener('click', async () => {
        try {
            const response = await fetch('http://localhost:8080/api/klanten');
            const klanten = await response.json();

            if (klanten.length === 0) {
                klantenDiv.innerHTML = "Geen klanten gevonden.";
            } else {
                let output = '';
                klanten.forEach(k => {
                    output += `
                        <strong>ID:</strong> ${k.id}<br>
                        <strong>Voornaam:</strong> ${k.voornaam}<br>
                        <strong>Achternaam:</strong> ${k.achternaam}<br>
                        <strong>Telefoon:</strong> ${k.telefoon}<br>
                        <strong>Email:</strong> ${k.email}<br>
                        <strong>Balans:</strong> $ ${parseFloat(k.balans).toFixed(2)}<br>
                        <strong>Betaalmethode ID:</strong> ${k.betaalmethode ? k.betaalmethode.id : 'Geen'}<br><br>
                `;
                });
                klantenDiv.innerHTML = output;
            }

            klantenPopup.classList.remove('hidden');

        } catch (error) {
            console.error('Fout bij ophalen klanten:', error);
            klantenDiv.textContent = "Fout bij het ophalen van klanten.";
            klantenPopup.classList.remove('hidden');
        }
    });

    closePopup.addEventListener('click', () => {
        klantenPopup.classList.add('hidden');
    });

    // 1 ID ophalen via ID
    const klantByIdBtn = document.getElementById('getKlantByIdBtn');
    const klantByIdPopup = document.getElementById('klantByIdPopup');
    const closeKlantPopup = document.getElementById('closeKlantPopup');
    const klantByIdForm = document.getElementById('klantByIdForm');
    const klantIdInput = document.getElementById('klantIdInput');
    const klantByIdResult = document.getElementById('klantByIdResult');

    klantByIdBtn.addEventListener('click', () => {
        klantByIdPopup.classList.remove('hidden');
    });

    closeKlantPopup.addEventListener('click', () => {
        klantByIdPopup.classList.add('hidden');
        klantByIdResult.textContent = '';
        klantIdInput.value = '';
    });

    klantByIdForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const id = klantIdInput.value.trim();

        if (!id) {
            klantByIdResult.textContent = "Voer een klant-ID in.";
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/klanten/${id}`);

            if (!response.ok) {
                if (response.status === 404) {
                    klantByIdResult.innerHTML = `Klant met ID <strong>${id}</strong> niet gevonden.`;
                } else {
                    klantByIdResult.textContent = `Fout: ${response.status} - ${response.statusText}`;
                }
                return;
            }

            const klant = await response.json();
            klantByIdResult.innerHTML = `
                <strong>ID:</strong> ${klant.id}<br>
                <strong>Voornaam:</strong> ${klant.voornaam}<br>
                <strong>Achternaam:</strong> ${klant.achternaam}<br>
                <strong>Email:</strong> ${klant.email}<br>
                <strong>Telefoon:</strong> ${klant.telefoon}<br>
                <strong>Balans:</strong> $ ${parseFloat(klant.balans).toFixed(2)}<br>
                <strong>Betaalmethode ID:</strong> ${klant.betaalmethode ? klant.betaalmethode.id : 'Geen'}
        `;
        } catch (error) {
            console.error("Fout bij ophalen klant:", error);
            klantByIdResult.textContent = "Er is een fout opgetreden bij het ophalen.";
        }
    });

    // PUTT

    document.getElementById('updateKlantForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const id = document.getElementById('update-id').value.trim();
        if (!id) {
            alert("Klant ID is verplicht!");
            return;
        }

        const updatedKlant = {
            voornaam: document.getElementById('update-voornaam').value.trim(),
            achternaam: document.getElementById('update-achternaam').value.trim(),
            telefoon: document.getElementById('update-telefoon').value.trim(),
            email: document.getElementById('update-email').value.trim(),
            balans: parseFloat(document.getElementById('update-balans').value) || 0,
            betaalmethode: {
                id: parseInt(document.getElementById('update-betaalmethode').value) || null
            }
        };

        try {
            const response = await fetch(`http://localhost:8080/api/klanten/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedKlant)
            });

            if (!response.ok) throw new Error("Update mislukt.");

            const result = await response.json();
            document.getElementById('updateResponse').innerHTML = `
              <b>Klant bijgewerkt:</b><br>
              ID: ${id}<br>
              Voornaam: ${result.voornaam}<br>
              Achternaam: ${result.achternaam}<br>
              Telefoon: ${result.telefoon}<br>
              Email: ${result.email}<br>
              Balans: €${result.balans}<br>
              Betaalmethode ID: ${result.betaalmethode?.id || 'N.v.t.'}
    `;
        } catch (error) {
            console.error("Fout bij bijwerken klant:", error);
            document.getElementById('updateResponse').textContent = "Klantupdate mislukt.";
        }
    });

});