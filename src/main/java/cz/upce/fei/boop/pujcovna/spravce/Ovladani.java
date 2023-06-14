package cz.upce.fei.boop.pujcovna.spravce;

import cz.upce.fei.boop.pujcovna.data.Letadlo;
import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;

public interface Ovladani extends Iterable<Letadlo>{

    /**
     * Vloží letadlo do seznamu letadel.
     *
     * @param letadlo letadlo, které se má vložit
     * @throws OvladaniException pokud je letadlo null
     */
    void vlozPolozku(Letadlo letadlo) throws OvladaniException;

    /**
     * Najde letadlo v seznamu podle jeho identifikátoru.
     *
     * @param id identifikátor letadla
     * @return nalezené letadlo nebo null, pokud letadlo s daným identifikátorem neexistuje v seznamu
     */
    Letadlo najdi(String id) throws OvladaniException;

    /**
     * Odebere letadlo ze seznamu podle jeho identifikátoru.
     *
     * @param id identifikátor letadla
     * @return odebrané letadlo nebo null, pokud letadlo s daným identifikátorem neexistuje v seznamu
     */
    Letadlo odeber(String id) throws OvladaniException;

    /**
     * Vrátí aktuální letadlo v seznamu letadel.
     *
     * @return aktuální letadlo v seznamu nebo null, pokud seznam je prázdný
     */
    Letadlo dej() throws OvladaniException;

    /**
     * edituje aktuální letadlo v seznamu letadel
     * @param data
     * @throws OvladaniException pokud je letadlo null, seznam je prázdný nebo nenastaven aktuální prvek
     */
    void editAktualni(Letadlo data) throws OvladaniException;

    /**
     * edituje letadlo v seznamu letadel
     * @param id
     * @param letadlo
     * @throws OvladaniException pokud je letadlo null nebo seznam je prázdný
     */
    void update(String id, Letadlo letadlo) throws OvladaniException;

    /**
     * Vyjme aktuální letadlo ze seznamu letadel.
     *
     * @return vyjmuté letadlo nebo null, pokud seznam je prázdný
     * @throws OvladaniException pokud není nastaven aktuální prvek
     */
    Letadlo vyjmi() throws OvladaniException;

    /**
     * Nastaví aktuální letadlo v seznamu na první prvek.
     */
    void nastavPrvni() throws OvladaniException;

    /**
     * Posune aktuální letadlo v seznamu na další prvek.
     */
    void dalsi() throws OvladaniException;

    /**
     * Nastaví aktuální letadlo v seznamu na poslední prvek.
     */
    void nastavPosledni() throws OvladaniException;

    /**
     * Vrátí počet letadel v seznamu.
     *
     * @return počet letadel v seznamu
     */
    int pocet();

    /**
     * Obnoví seznam letadel ze záložní kopie.
     */
    void obnov() throws OvladaniException;

    /**
     * Zálohuje aktuální stav seznamu letadel.
     */
    void zalohuj() throws OvladaniException;

    /**
     * Vrátí seznam letadel v textové podobě.
     *
     * @return seznam letadel v textové podobě
     */
    SpojovySeznam<Letadlo> dejSeznam() throws OvladaniException;

    /**
     * Načte seznam letadel ze souboru v textovém formátu.
     */
    void nactiText() throws OvladaniException;

    /**
     * Uloží seznam letadel do souboru v textovém formátu.
     */
    void ulozText();

    /**
     * Vytvoří a přidá zadaný počet nových náhodných letadel do seznamu letadel.
     * @param pocet počet letadel k vytvoření a přidání
     */
    void generuj(int pocet);

    /**
     * Odstraní všechna letadla ze seznamu a uvolní přidružené zdroje.
     */
    void zrus();

}
