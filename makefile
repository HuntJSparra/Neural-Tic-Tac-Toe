NNDIR = hjs/nnpackage

NNPackage:
	javac $(NNDIR)/pairs/*.java
	javac $(NNDIR)/network/*.java
	javac $(NNDIR)/learning/*.java

TicTacToe.class: NNPackage
	javac -d ./ TicTacToe/*.java

run: TicTacToe.class
	java TicTacToe

clean:
	find . -name "*.class" -type f -delete