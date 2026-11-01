<img width="691" height="988" alt="image" src="https://github.com/user-attachments/assets/003f8960-68a4-450f-95a2-82b36e4ff299" />


Dodano przycisk „Wyczyść formularz”, który po potwierdzeniu usuwa dane ze wszystkich pól.
Przed wyczyszczeniem formularza zapisuję jego poprzedni stan w obiekcie `FormState`. Przechowuję w nim teksty, wybrane opcje, zaznaczenia oraz ustawienia suwaka i przełącznika.
Po wyczyszczeniu pojawia się Snackbar z przyciskiem „COFNIJ”. Po jego kliknięciu metoda `restoreFormState()` przywraca wcześniej zapisane dane.
Dzięki temu  można odzyskać formularz bez konieczności ponownego wpisywania wszystkich informacji.
