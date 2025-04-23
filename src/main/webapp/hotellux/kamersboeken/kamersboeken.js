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
});