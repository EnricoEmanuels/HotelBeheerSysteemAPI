document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("boekForm");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        // Input values
        const startdatum = document.getElementById("startdatum").value;
        const einddatum = document.getElementById("einddatum").value;
        const klant_id = document.getElementById("klant_id").value;
        const kamer_id = document.getElementById("beschikbareKamer_id").value;
        const betaal_id = document.getElementById("betaalmethode_id").value;

        // Error elements
        const klantError = document.getElementById("klantError");
        const kamerError = document.getElementById("kamerError");
        const betaalError = document.getElementById("betaalError");

        // Clear previous errors
        klantError.textContent = "";
        kamerError.textContent = "";
        betaalError.textContent = "";

        const isValid = await validateForm(klant_id, kamer_id, betaal_id);
        if (!isValid) return;

        // const boeking = {
        //     startdatum,
        //     einddatum,
        //     klant_id,
        //     beschikbareKamer_id: kamer_id,
        //     betaalmethode_id: betaal_id
        // };

        const boeking = {
            startdatum,
            einddatum,
            klant: { id: parseInt(klant_id) },
            beschikbareKamer: { id: parseInt(kamer_id) },
            betaalmethodes: { id: parseInt(betaal_id) }
        };





        try {
            const res = await fetch(`http://localhost:8080/api/kamersboeken/create`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(boeking)
            });

            if (res.ok) {
                alert("Boeking succesvol!");
                form.reset();
            } else {
                const data = await res.json();
                alert("Fout bij boeken: " + (data.message || res.status));
            }
        } catch (err) {
            alert("Netwerkfout of server offline.");
            console.error(err);
        }
    });

    async function validateForm(klantId, kamerId, betaalId) {
        const [klant, kamer, betaal] = await Promise.all([
            fetch(`http://localhost:8080/api/klanten/${klantId}`),
            fetch(`http://localhost:8080/api/beschikbarekamers/${kamerId}`),
            fetch(`http://localhost:8080/api/betaalmethodes/${betaalId}`)
        ]);

        let valid = true;

        if (!klant.ok) {
            document.getElementById("klantError").textContent = "Klant-ID bestaat niet.";
            valid = false;
        }
        if (!kamer.ok) {
            document.getElementById("kamerError").textContent = "Kamer-ID bestaat niet.";
            valid = false;
        }
        if (!betaal.ok) {
            document.getElementById("betaalError").textContent = "Betaalmethode-ID bestaat niet.";
            valid = false;
        }

        return valid;
    }

    // GET ALL

    const fetchBoekingenButton = document.getElementById('fetchBoekingenButton');
    const boekingenPopup = document.getElementById('boekingenPopup');
    const closeBoekingenPopup = document.getElementById('closeBoekingenPopup');
    const boekingenResultaat = document.getElementById('boekingenResultaat');

    fetchBoekingenButton.addEventListener('click', async () => {
        try {
            const response = await fetch('http://localhost:8080/api/kamersboeken');
            if (!response.ok) throw new Error('Netwerkfout of geen toegang');
            const boekingen = await response.json();

            if (!Array.isArray(boekingen) || boekingen.length === 0) {
                boekingenResultaat.textContent = "Geen boekingen gevonden.";
            } else {
                let output = '';

                const formatDate = (millis) => {
                    const date = new Date(millis);
                    return date.toISOString().split('T')[0]; // YYYY-MM-DD
                };

                boekingen.forEach(b => {
                    output += `
                    ID: ${b.id}
                    Startdatum: ${formatDate(b.startdatum)}
                    Einddatum: ${formatDate(b.einddatum)}
                    Klant ID: ${b.klant?.id ?? 'Onbekend'}
                    Beschikbare Kamer ID: ${b.beschikbareKamer?.id ?? 'Onbekend'}
                    Betaalmethode ID: ${b.betaalmethodes?.id ?? 'Onbekend'}
                    -----------------------------
`;
                });
                boekingenResultaat.textContent = output;
            }

            boekingenPopup.classList.remove('hidden');
        } catch (err) {
            console.error('Fout bij ophalen kamersboekingen:', err);
            boekingenResultaat.textContent = "Er is iets misgegaan tijdens het ophalen van de boekingen.";
            boekingenPopup.classList.remove('hidden');
        }
    });

    closeBoekingenPopup.addEventListener('click', () => {
        boekingenPopup.classList.add('hidden');
    });

    // GET BY ID

    const btn = document.getElementById("getBoekingByIdBtn");
    const popup = document.getElementById("boekingByIdPopup");
    const close = document.getElementById("closeBoekingPopup");
    const fform = document.getElementById("boekingByIdForm");
    const input = document.getElementById("boekingIdInput");
    const result = document.getElementById("boekingByIdResult");

    btn.onclick = () => popup.classList.remove("hidden");
    close.onclick = () => {
        popup.classList.add("hidden");
        result.textContent = "";
        input.value = "";
    };

    fform.onsubmit = async (e) => {
        e.preventDefault();
        const id = input.value.trim();
        if (!id) return result.textContent = "Geef een ID op.";

        try {
            const res = await fetch(`http://localhost:8080/api/kamersboeken/${id}`);
            if (!res.ok) return result.textContent = res.status === 404
                ? `Boeking met ID ${id} niet gevonden.` : `Fout: ${res.status}`;

            const b = await res.json();
            const d = (dt) => new Date(dt).toISOString().split("T")[0];

            const formatDate = (millis) => {
                const date = new Date(millis);
                return date.toISOString().split('T')[0]; // YYYY-MM-DD
            };
            result.innerHTML = `
                <strong>ID:</strong> ${b.id}<br>
                <strong>Startdatum: ${formatDate(b.startdatum)}
                <strong>Einddatum: ${formatDate(b.einddatum)}<br>
                <strong>Klant ID:</strong> ${b.klant?.id || 'Onbekend'}<br>
                <strong>Beschikbare Kamer ID:</strong> ${b.beschikbareKamer?.id || 'Onbekend'}<br>
                <strong>Betaalmethode ID:</strong> ${b.betaalmethodes?.id || 'Onbekend'}
      `;
        } catch (err) {
            result.textContent = "Er is een fout opgetreden.";
            console.error(err);
        }
    };

    // PUTTT

    document.getElementById("updateBoekingForm").addEventListener("submit", async (e) => {
        e.preventDefault();

        const id = document.getElementById("boeking_idd").value.trim();
        const startdatum = document.getElementById("startdatumm").value;
        const einddatum = document.getElementById("einddatumm").value;
        const klant_id = document.getElementById("klant_idd").value.trim();
        const kamer_id = document.getElementById("beschikbareKamer_idd").value.trim();
        const betaal_id = document.getElementById("betaalmethode_idd").value.trim();

        // Foutmeldingen resetten
        document.getElementById("klantErrorr").textContent = "";
        document.getElementById("kamerErrorr").textContent = "";
        document.getElementById("betaalErrorr").textContent = "";

        const isValid = await validateForm(klant_id, kamer_id, betaal_id);
        if (!isValid) return;

        const boeking = {
            id: parseInt(id),
            startdatum,
            einddatum,
            klant: { id: parseInt(klant_id) },
            beschikbareKamer: { id: parseInt(kamer_id) },
            betaalmethodes: { id: parseInt(betaal_id) }
        };

        try {
            const res = await fetch(`http://localhost:8080/api/kamersboeken/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(boeking)
            });

            const result = await res.json();

            const formatDate = (millis) => {
                const date = new Date(millis);
                return date.toISOString().split('T')[0]; // YYYY-MM-DD
            };

            if (res.ok) {
                document.getElementById("updateBoekingResponse").textContent =
                    `Boeking succesvol bijgewerkt 
                    \nStartdatum: ${formatDate(result.startdatum)}
                    \nEinddatum: ${formatDate(result.einddatum)}
                    \nKlant-ID: ${result.klant?.id}
                    \nKamer-ID: ${result.beschikbareKamer?.id}
                    \nBetaalmethode-ID: ${result.betaalmethodes?.id}`;
            } else {
                document.getElementById("updateBoekingResponse").textContent = "Fout bij bijwerken: " + (result.message || res.status);
            }
        } catch (err) {
            console.error("Netwerkfout:", err);
            document.getElementById("updateBoekingResponse").textContent = "Serverfout of netwerkprobleem.";
        }
    });

    async function validateForm(klantId, kamerId, betaalId) {
        const [klant, kamer, betaal] = await Promise.all([
            fetch(`http://localhost:8080/api/klanten/${klantId}`),
            fetch(`http://localhost:8080/api/beschikbarekamers/${kamerId}`),
            fetch(`http://localhost:8080/api/betaalmethodes/${betaalId}`)
        ]);

        let valid = true;

        if (!klant.ok) {
            document.getElementById("klantError").textContent = "Klant-ID bestaat niet.";
            valid = false;
        }
        if (!kamer.ok) {
            document.getElementById("kamerError").textContent = "Beschikbare Kamer-ID bestaat niet.";
            valid = false;
        }
        if (!betaal.ok) {
            document.getElementById("betaalError").textContent = "Betaalmethode-ID bestaat niet.";
            valid = false;
        }

        return valid;
    }

    // voor die DELETE
    document.getElementById("deleteBoekingForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        const id = document.getElementById("boeking-id").value.trim();
        const msg = document.getElementById("boekingDeleteMessage");

        if (!id) {
            msg.textContent = "Geef een geldig ID op.";
            msg.style.color = "orange";
            return;
        }

        try {
            const res = await fetch(`http://localhost:8080/api/kamersboeken/${id}`, {
                method: "DELETE",
            });

            if (res.status === 204) {
                msg.textContent = `Boeking met ID ${id} is verwijderd.`;
                msg.style.color = "limegreen";
                return;
            }

            if (res.status === 404) {
                msg.textContent = `Boeking met ID ${id} bestaat niet.`;
                msg.style.color = "orangered";
                return;
            }

            msg.textContent = `Er ging iets fout tijdens verwijderen.`;
            msg.style.color = "red";
        } catch (fout) {
            console.error("Fout:", fout);
            msg.textContent = "Verbinding mislukt of server offline.";
            msg.style.color = "red";
        }
    });
});