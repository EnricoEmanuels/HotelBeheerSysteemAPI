// PUT VOOR KAMER
document.addEventListener('DOMContentLoaded', () => {
    console.log("PUT-script is geladen.");
    const kamerUpdateID = document.getElementById('kamer-update-id');
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
        console.log("Formulier ingediend...");

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
                Prijs per maand: $${parseFloat(result.prijsPerMaand).toFixed(2)}
            `;
        } catch (error) {
            console.error("Fout bij bijwerken kamer:", error);
            kamerUpdateResponse.textContent = "Kamerupdate mislukt.";
        }
    });
});

