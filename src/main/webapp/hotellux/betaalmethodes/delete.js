document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('deleteBetaalmethodeForm');
    const idInput = document.getElementById('betaalmethode-idd');
    const messageEl = document.getElementById('betaalmethodeDeleteMessage');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = idInput.value.trim();

        if (!id || isNaN(id) || parseInt(id) < 1) {
            messageEl.textContent = "Voer een geldig Betaalmethode ID in.";
            messageEl.style.color = "orangered";
            return;
        }

        try {
            const res = await fetch(`http://localhost:8080/api/betaalmethodes/${id}`, {
                method: 'DELETE'
            });

            if (res.status === 200) {
                messageEl.textContent = `Betaalmethode met ID ${id} succesvol verwijderd.`;
                messageEl.style.color = "limegreen";
            } else if (res.status === 404) {
                messageEl.textContent = `Betaalmethode met ID ${id} niet gevonden.`;
                messageEl.style.color = "orange";
            } else {
                messageEl.textContent = "Er is een fout opgetreden bij het verwijderen.";
                messageEl.style.color = "red";
            }
        } catch (error) {
            console.error("Verwijderfout:", error);
            messageEl.textContent = "Netwerkfout of server onbereikbaar.";
            messageEl.style.color = "red";
        }
    });
});

