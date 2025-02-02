import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String [] args) throws FileNotFoundException,ArrayIndexOutOfBoundsException,ExInvalidDateFormat {	
		
		Scanner in = new Scanner(System.in);
		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		
		Scanner inFile = new Scanner(new File(filepathname));

		String cmdLine1 = inFile.nextLine();
		String[] cmdLine1Parts = cmdLine1.split(" ");
		System.out.println("\n> "+cmdLine1);
		SystemDate.createTheInstance(cmdLine1Parts[1]);
		
		while (inFile.hasNext()) {
			String cmdLine = inFile.nextLine().trim();
			
			if (cmdLine.equals("")) continue;  
			System.out.println("\n> "+cmdLine);
			String[] cmdParts = cmdLine.split(" "); 
			try {
			if (cmdParts[0].equals("register")){
				(new CmdRegister()).execute(cmdParts);
			}
			else if (cmdParts[0].equals("listMembers")){
				(new CmdListMembers()).execute(cmdParts);
			}
			else if (cmdParts[0].equals("startNewDay")){
				(new CmdStartNewDay()).execute(cmdParts);
			}
			else if (cmdParts[0].equals("undo")){
				RecordedCommand.undoOneCommand();
			}
			else if (cmdParts[0].equals("redo")){
				RecordedCommand.redoOneCommand();
			}
			else if(cmdParts[0].equals("create")){
				(new CmdCreateNewEquipment()).execute(cmdParts);
			}
			else if (cmdParts[0].equals("listEquipment")){
				(new CmdListEquipment()).execute(cmdParts);
			}
			else if (cmdParts[0].equals("arrive")){
				(new CmdEquipmentArrived()).execute(cmdParts);
			}
			else if (cmdParts[0].equals("borrow")){
				(new CmdEquipmentBorrow()).execute(cmdParts);
			}
			else if (cmdParts[0].equals("listEquipmentStatus")){
				(new CmdListEquipmentStatus()).execute(cmdParts);
			}
			else if (cmdParts[0].equals("listMemberStatus")) {
				(new CmdListMemberStatus()).execute(cmdParts);
			} else if (cmdParts[0].equals("request")) 
			{
				(new CmdEquipmentRequest()).execute(cmdParts);
			}
			else{
				throw new ExUnkownCommand();
			}
			// catching all the relevant errors.
			}
			catch (ArrayIndexOutOfBoundsException A) {
				System.out.println("Insufficient command arguments.");
			}
			catch (ExUnkownCommand UC) {
				System.out.println(UC.getMessage());
				break;
			}
			catch(ExDuplicateMemberID e){
				System.out.println(e.getMessage());
			}
			catch(ExDuplicateEquipment e){
				System.out.println(e.getMessage());
			}
			catch(ExInvalidDate e){
				System.out.println(e.getMessage());
			}
			catch(ExInvalidDateFormat e)
			{
				System.out.println(e.getMessage());
			}
			catch(ExEquipmentNotFound e)
			{
				System.out.println(e.getMessage());
			}
			catch(ExMemberNotFound e)
			{
				System.out.println(e.getMessage());
			}
			catch(ExEquipmentSetNotAvailable e){
				System.out.println(e.getMessage());
			}
			catch(ExInavlidRequestDays e){
				System.out.println(e.getMessage());
			}
			catch(ExNegativeRequestDays e){
				System.out.println(e.getMessage());
			}
			catch(ExOverlappingRequest e){
				System.out.println(e.getMessage());
			}
			catch(ExOverlappingRequestAndBorrow e){
				System.out.println(e.getMessage());
			}
			catch(ExSetIsNotAvailable e){
				System.out.println(e.getMessage());
			}
			catch(ExDoubleBorrow e){
				System.out.println(e.getMessage());
			}
			catch(ExEquipmentRecordMissing e){
				System.out.println(e.getMessage());
			}
		}

		inFile.close();
		in.close();
	}
}
