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
});
