all: $(wildcard *.java) $(wildcard AccountTypes/*.java)
	javac *.java
 
clean: 
	rm -f *.class *~ *#
	rm -f AccountTypes/*.class *~ *#
