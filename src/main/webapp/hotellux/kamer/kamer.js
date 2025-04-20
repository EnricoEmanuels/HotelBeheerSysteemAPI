document.addEventListener('DOMContentLoaded', () => {
    const kamerForm = document.getElementById('kamerForm');
    const kamertypeSelect = document.getElementById('kamertype');
    const aantalbeddenInput = document.getElementById('aantalbedden');
    const prijspermaandInput = document.getElementById('prijspermaand');
    const validationMsg = document.getElementById('kamertype-validation');
    const responseDiv = document.getElementById('createResponse');

    const kamerData = {
        goedkoop: { bedden: 1, prijs: 300 },
        normaal: { bedden: 2, prijs: 600 },
        deftig: { bedden: 4, prijs: 1000 }
    };

    kamertypeSelect.addEventListener('change', () => {
        const type = kamertypeSelect.value;
        if (kamerData[type]) {
            aantalbeddenInput.value = kamerData[type].bedden;
            prijspermaandInput.value = kamerData[type].prijs;
            validationMsg.style.display = 'none';
        }
    });

    kamerForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const kamertype = kamertypeSelect.value;
        if (!kamertype) {
            validationMsg.style.display = 'block';
            return;
        }

        const kamer = {
            kamertype,
            aantalbedden: kamerData[kamertype].bedden,
            prijsPerMaand: kamerData[kamertype].prijs
        };

        try {
            const response = await fetch('http://localhost:8080/api/kamers/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(kamer)
            });

            const result = await response.json();

            responseDiv.innerHTML = `
        Kamer succesvol aangemaakt:<br>
        <b>ID:</b> ${result.id}<br>
        <b>Type:</b> ${result.kamertype}<br>
        <b>Bedden:</b> ${result.aantalbedden}<br>
        <b>Prijs/maand:</b> $ ${result.prijsPerMaand}
      `;
            kamerForm.reset();
            aantalbeddenInput.value = '';
            prijspermaandInput.value = '';
        } catch (error) {
            console.error('Fout bij het aanmaken van kamer:', error);
            alert("Er ging iets mis.");
        }
    });

    // get alle methodes

    const fetchKamersButton = document.getElementById('fetchKamersButton');
    const kamersPopup = document.getElementById('kamersPopup');
    const closeKamersPopup = document.getElementById('closeKamersPopup');
    const kamersDiv = document.getElementById('kamers');

    fetchKamersButton.addEventListener('click', async () => {
        try {
            const response = await fetch('http://localhost:8080/api/kamers');
            const kamers = await response.json();

            if (kamers.length === 0) {
                kamersDiv.innerHTML = "Geen kamers gevonden.";
            } else {
                let output = '';
                kamers.forEach(k => {
                    output += `
                    <strong>ID:</strong> ${k.id}<br>
                    <strong>Type:</strong> ${k.kamertype}<br>
                    <strong>Bedden:</strong> ${k.aantalbedden}<br>
                    <strong>Prijs/maand:</strong> $ ${parseFloat(k.prijsPerMaand).toFixed(2)}<br><br>
                `;
                });
                kamersDiv.innerHTML = output;
            }

            kamersPopup.classList.remove('hidden');

        } catch (error) {
            console.error('Fout bij ophalen kamers:', error);
            kamersDiv.textContent = "Fout bij het ophalen van kamers.";
            kamersPopup.classList.remove('hidden');
        }
    });

    closeKamersPopup.addEventListener('click', () => {
        kamersPopup.classList.add('hidden');
    });

    // specifieke ID halen van kamer

    document.getElementById('getKamerForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const id = document.getElementById('kamer-id').value.trim();
        const resultBox = document.getElementById('kamerInfo');
        const details = document.getElementById('kamerDetails');

        if (!id) {
            details.innerHTML = "Voer een geldig kamer-ID in.";
            resultBox.classList.remove('hidden');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/kamers/${id}`);

            if (!response.ok) {
                details.innerHTML = `Kamer met ID <b>${id}</b> niet gevonden.`;
                resultBox.classList.remove('hidden');
                return;
            }

            const kamer = await response.json();

            // Let op: juist gespelde keys van je DTO gebruiken!
            details.innerHTML = `
            <b>ID:</b> ${kamer.id}<br>
            <b>Type:</b> ${kamer.kamertype}<br>
            <b>Aantal Bedden:</b> ${kamer.aantalbedden}<br>
            <b>Prijs per Maand:</b> € ${parseFloat(kamer.prijsPerMaand).toFixed(2)}<br>
        `;

            resultBox.classList.remove('hidden');
        } catch (error) {
            console.error('Fout bij het ophalen van kamer:', error);
            details.innerHTML = "Er is een fout opgetreden bij het ophalen van de kamer.";
            resultBox.classList.remove('hidden');
        }
    });

    // PUT VOOR KAMER
    document.addEventListener('DOMContentLoaded', () => {
        const kamerTypeSelect = document.getElementById('kamer-update-type');
        const beddenInput = document.getElementById('kamer-update-bedden');
        const prijsInput = document.getElementById('kamer-update-prijs');
        const kamerUpdateForm = document.getElementById('updateKamerForm');
        const kamerUpdateResponse = document.getElementById('kamerUpdateResponse');

        const kamerData = {
            goedkoop: { bedden: 1, prijs: 300 },
            normaal: { bedden: 2, prijs: 600 },
            deftig: { bedden: 4, prijs: 1000 }
        };

        // Automatisch invullen wanneer type verandert
        kamerTypeSelect.addEventListener('change', () => {
            const selected = kamerTypeSelect.value;
            if (kamerData[selected]) {
                beddenInput.value = kamerData[selected].bedden;
                prijsInput.value = kamerData[selected].prijs;
            }
        });

        // Submit handler
        kamerUpdateForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const id = document.getElementById('kamer-update-id').value.trim();
            const kamertype = kamerTypeSelect.value;
            const bedden = parseInt(beddenInput.value);
            const prijs = parseFloat(prijsInput.value);

            if (!id || !kamertype || isNaN(bedden) || isNaN(prijs)) {
                alert("Vul alle velden correct in.");
                return;
            }

            const updatedKamer = {
                kamertype: kamertype,
                aantalbedden: bedden,
                prijsPerMaand: prijs
            };

            try {
                const response = await fetch(`http://localhost:8080/api/kamers/${id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(updatedKamer)
                });

                if (!response.ok) {
                    kamerUpdateResponse.textContent = `Fout ${response.status}: bijwerken mislukt.`;
                    return;
                }

                const result = await response.json();

                kamerUpdateResponse.innerHTML = `
                <b>Kamer bijgewerkt:</b><br>
                ID: ${result.id}<br>
                Kamertype: ${result.kamertype}<br>
                Aantal bedden: ${result.aantalbedden}<br>
                Prijs per maand: €${parseFloat(result.prijsPerMaand).toFixed(2)}
            `;
            } catch (error) {
                console.error("Fout bij bijwerken kamer:", error);
                kamerUpdateResponse.textContent = "Kamerupdate mislukt.";
            }
        });
    });



});
