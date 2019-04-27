# pdf-encryptor
Encrypts pdf file with given password

Log passed args

Log filename and password to list


@echo off

java -jar encryptor.jar -p %1 -f %2


Attribute VB_Name = "Module1"

Public Sub SaveEncryptPDF()
  
    Dim pdfName As String
    pdfName = "./" & ActiveSheet.Name & ".pdf"
    
    ActiveSheet.ExportAsFixedFormat Type:=xlTypePDF, _
    Filename:=pdfName, _
    Quality:=xlQualityStandard, IncludeDocProperties:=True, _
    IgnorePrintAreas:=False, OpenAfterPublish:=False
    
    Shell ("encryptor.bat 123456 " & pdfName)
End Sub


