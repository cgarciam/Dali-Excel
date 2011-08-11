package mx.home.util;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jxl.read.biff.BiffException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReaderTest {

	@Test(expected = BiffException.class)
	// TODO? To transform in other kind of exception??? maybe a runtime???
	public void testLeerArchivoExcelFormatoNoSoportado() throws BiffException,
			IOException {
		new ExcelReader().leerArchivoExcel(readInputFile(DIR_PATHFILE_NAME
				+ "circuits_johnson.pdf"));
	}

	@Test()
	// TODO? To transform in other kind of exception??? maybe a runtime???
	public void testLeerArchivoExcelNoExiste() throws BiffException,
			IOException {
		new ExcelReader().leerArchivoExcel(DIR_PATHFILE_NAME + "noExisto.xyz");
	}

	@Test()
	public void testLeerArchivoExcel() throws BiffException, IOException {
		final ExcelReader excelReader = new ExcelReader();
		final List<List<String>> archivoLeido = excelReader
				.leerArchivoExcel(readInputFile(DIR_PATHFILE_NAME
						+ "Libro5.xls"));
		final String cellsFromXslFile = getMessageLog(archivoLeido);
		LOG.debug("Read cells:\n" + cellsFromXslFile);
	}

	private String getMessageLog(final List<List<String>> archivoLeido) {
		final StringBuilder dataInXslFile = new StringBuilder();
		for (List<String> row : archivoLeido) {
			for (String cell : row) {
				dataInXslFile.append(cell);
				dataInXslFile.append("\n");
			}
		}
		return dataInXslFile.toString();
	}

	private InputStream readInputFile(final String fileName) {
		InputStream inputFile = null; // NOPMD by Cesar on 11/08/11 1:02
		try {
			// inputFile = new NDocumentInputStream(document);
			inputFile = new FileInputStream(fileName);
		} catch (IOException e) {
			final StringBuilder msgErr = new StringBuilder();
			msgErr.append("Error al leer el archivo de entrada. ");
			msgErr.append("Verifique que el archivo ");
			msgErr.append(fileName);
			msgErr.append(" exista.");
			LOG.error("In readInputFile()", e);
			assertNotNull(msgErr.toString(), inputFile);
		}
		return inputFile;
	}

	private static final Logger LOG;
	private static final String DIR_PATHFILE_NAME;
	// private static final String ELEM_SEPARATOR = " ";

	static {
		LOG = LoggerFactory.getLogger(ExcelReaderTest.class);
		DIR_PATHFILE_NAME = "C:/Users/Cesar/Documents/development/BMV/";
	}

}
