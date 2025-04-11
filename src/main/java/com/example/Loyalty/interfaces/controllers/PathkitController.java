package com.example.Loyalty.interfaces.controllers;

import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import com.example.Loyalty.usecases.EnterpriseUseCase;
import com.google.common.net.HttpHeaders;
import de.brendamour.jpasskit.PKBarcode;
import de.brendamour.jpasskit.PKField;
import de.brendamour.jpasskit.PKPass;
import de.brendamour.jpasskit.enums.PKBarcodeFormat;
import de.brendamour.jpasskit.enums.PKPassType;
import de.brendamour.jpasskit.passes.PKGenericPass;
import de.brendamour.jpasskit.signing.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Locale;
import java.util.Properties;



@RestController
@RequestMapping("/pathkit")
public class PathkitController {

    private static final String PASS_TEMPLATE_FOLDER = "src/main/resources/apple-wallet/templates";
    private static final String KEYPATH = "src/main/resources/apple-wallet/certificates/CERTIFICATE.p12";
    private static final String KEY_PASSWORD = "d97GDKSN"; // À stocker dans config.properties
    private static final String WWDRCA_PATH = "src/main/resources/apple-wallet/certificates/apple-cert.pem";

    @GetMapping("/generate-pass")
    public ResponseEntity<InputStreamResource> generatePass() throws IOException, CertificateException, PKSigningException {
        // Création du pass avec jPasskit
        PKPass pass = PKPass.builder()
                .pass(
                        PKGenericPass.builder()
                                .passType(PKPassType.PKStoreCard)
                                .primaryFieldBuilder(
                                        PKField.builder()
                                                .key("balance")
                                                .label("balance")
                                                .value(20.0)
                                                .currencyCode("EUR")
                                )
                )
                .barcodeBuilder(
                        PKBarcode.builder()
                                .format(PKBarcodeFormat.PKBarcodeFormatQR)
                                .message("ABCDEFG")
                                .messageEncoding(java.nio.charset.StandardCharsets.UTF_8)
                )
                .formatVersion(1)
                .passTypeIdentifier("pass.com.example.royaltis")
                .serialNumber("000000001")
                .teamIdentifier("com.anthonycorydp.royalties")
                .organizationName("royalties")
                .logoText("MyPass")
                .description("My PassBook")
                .backgroundColor(java.awt.Color.BLACK)
                .foregroundColor("rgb(255,255,255)")
                .build();

        // Charger les informations de signature
        PKSigningInformation pkSigningInformation = new PKSigningInformationUtil()
                .loadSigningInformationFromPKCS12AndIntermediateCertificate(KEYPATH, KEY_PASSWORD, WWDRCA_PATH);

        // Charger le modèle de pass depuis le dossier
        PKPassTemplateFolder passTemplate = new PKPassTemplateFolder(PASS_TEMPLATE_FOLDER);

        // Signer et zipper le pass
        PKFileBasedSigningUtil pkSigningUtil = new PKFileBasedSigningUtil();
        byte[] signedAndZippedPkPassArchive = pkSigningUtil.createSignedAndZippedPkPassArchive(pass, passTemplate, pkSigningInformation);

        // Sauvegarder le pass zippé dans un fichier
        File pkpassFile = new File("loyalty-card.pkpass");
        try (FileOutputStream fos = new FileOutputStream(pkpassFile)) {
            fos.write(signedAndZippedPkPassArchive);
        }

        // Retourner le fichier .pkpass en tant que téléchargement
        FileInputStream fileInputStream = new FileInputStream(pkpassFile);
        InputStreamResource resource = new InputStreamResource(fileInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=loyalty-card.pkpass")
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.apple.pkpass")
                .contentLength(pkpassFile.length())
                .body(resource);
    }

//    @PostMapping("/personalize-pass/{serialNumber}")
//    public ResponseEntity<String> personalizePass(@PathVariable String serialNumber, @RequestBody UserInformation userInfo) {
//        // Ici, tu peux personnaliser le pass en fonction des informations utilisateur
//        // Par exemple, stocker les informations dans une base de données
//        // et les lier au serialNumber du pass.
//
//        // Une fois ces informations stockées, lorsque le pass sera téléchargé de nouveau,
//        // tu lui fourniras une version personnalisée.
//
//        return ResponseEntity.ok("Pass personalized successfully");
//    }

    // Ajouter d'autres méthodes si nécessaire, par exemple pour gérer les mises à jour de pass
}
