/*
 * PDFGenerationException.java					27/11/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package modeles.erreur;

/**
 * Exception personnalisée pour les erreurs de génération de PDF.
 */
public class PDFGenerationException extends Exception {

    /**
     * Constructeur avec un message d'erreur.
     *
     * @param message Le message décrivant l'erreur.
     */
    public PDFGenerationException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message d'erreur et une cause.
     *
     * @param message Le message décrivant l'erreur.
     * @param cause   L'exception sous-jacente qui a causé cette erreur.
     */
    public PDFGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}