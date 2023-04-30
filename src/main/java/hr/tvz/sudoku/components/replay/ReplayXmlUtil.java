package hr.tvz.sudoku.components.replay;

import hr.tvz.sudoku.components.board.GameMove;
import hr.tvz.sudoku.components.board.GameState;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hr.tvz.sudoku.control.SaveHandler.save;
import static hr.tvz.sudoku.util.InformUser.showMessage;

public class ReplayXmlUtil {
	private static final String REPLAY_FILE_NAME = "moves.xml";
	static final String INITIAL_STATE_FILE_NAME = "initialState.bin";

	private static int arrayIndex;
	
	private ReplayXmlUtil() { }
	
	public static void createReplayFiles(List<GameMove> gameMoveList, GameState.BoardState initialState) {
		try {
			save(initialState, INITIAL_STATE_FILE_NAME);
			
			Document document = createDocument();

			for(GameMove gameMove : gameMoveList) {
				Element gameMoveElement = document.createElement("GameMove");

				gameMoveElement.appendChild(createElement(document, "number", gameMove.getNumber()));
				gameMoveElement.appendChild(createElement(document, "row", gameMove.getRow()));	
				gameMoveElement.appendChild(createElement(document, "col", gameMove.getCol()));

				document.getDocumentElement().appendChild(gameMoveElement);
			}

			saveDocument(document);
			showMessage("Save replay", "Successfully saved a replay!", "");
		} catch (IOException | TransformerException | ParserConfigurationException e) {
			e.printStackTrace();
			showMessage("Replay", "Failed to save a replay!", e.getMessage());
		}
	}

	public static Optional<List<GameMove>> load() {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	
			Document document = builder.parse(new File(REPLAY_FILE_NAME));

			List<GameMove> gameMoveList = parse(document);
	
			return Optional.of(gameMoveList);
		} catch (Exception e) {
			e.printStackTrace();
			showMessage("Replay", "Failed to load the moves!", e.getMessage());
			return Optional.empty();
		}
	}

	private static Document createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation domImplementation = builder.getDOMImplementation();
		DocumentType documentType = domImplementation.createDocumentType("DOCTYPE", null, null);
		return domImplementation.createDocument(null, "GameMoves", documentType);
	}

	private static Node createElement(Document document, String tagName, int data) {
		Element element = document.createElement(tagName);
		Text text = document.createTextNode(String.valueOf(data));
		element.appendChild(text);
		return element;
	}

	private static void saveDocument(Document document) throws TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(new DOMSource(document), new StreamResult(new File(REPLAY_FILE_NAME)));
	}

	private static List<GameMove> parse(Document document) {
		List<GameMove> gameMoveList = new ArrayList<>();

		arrayIndex = -1;
		processNode(gameMoveList, document.getDocumentElement(), "");

		return gameMoveList;
	}
	
	private static void processNode(List<GameMove> gameMoveList, Node node, String status) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			status = node.getNodeName();
		} else if (node.getNodeType() == Node.TEXT_NODE) {
			String value = node.getNodeValue().trim();
			switch (status) {
				case "number" -> {
					gameMoveList.add(new GameMove());
					arrayIndex++;
					gameMoveList.get(arrayIndex).setNumber(Integer.parseInt(value));
				}
				case "col" -> gameMoveList.get(arrayIndex).setCol(Integer.parseInt(value));
				case "row" -> gameMoveList.get(arrayIndex).setRow(Integer.parseInt(value));
				case "GameMoves", "GameMove" -> { }
				default -> throw new IllegalStateException("Unexpected value: " + status);
			}
		}

		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) 
			processNode(gameMoveList, childNodes.item(i), status);
	}
}
