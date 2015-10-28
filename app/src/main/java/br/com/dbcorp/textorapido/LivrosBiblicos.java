package br.com.dbcorp.textorapido;

public enum LivrosBiblicos {

	GENESIS("Gênesis", "Gen"),
	EXODO("Êxodo", "Êx."),
	LEVITICO("Levítico", "Leví."),
	NUMEROS("Números", "Núm."),
	DEUTERONOMIO("Deuteronômio", "Deut."),
	JOSUE("Josué", "Jos."),
	JUIZES("Juízes", "Juí."),
	RUTE("Rute", "Rute"),
	SAMUEL1("1 Samuel", "1 Sam."),
	SAMUEL2("2 Samuel", "2 Sam."),
	REIS1("1 Reis", "1 Reis"),
	REIS2("2 Reis", "2 Reis"),
	CRONICAS1("1 Crônicas", "1 Crôn."),
	CRONICAS2("2 Crônicas", "2 Crôn."),
	ESDRAS("Esdras", "Esd."),
	NEEMIAS("Neemias", "Neem."),
	ESTER("Ester", "Ester"),
	JO("Jó", "Jó"),
	SALMOS("Salmos", "Sal."),
	PROVERBIOS("Provérbios", "Prov."),
	ECLESIASTES("Eclesiastes", "Ecl."),
	CANTICO("Cântico de Salomão", "Cântico"),
	ISAIAS("Isaías", "Isa."),
	JEREMIAS("Jeremias", "Jere."),
	LAMENTACOES("Lamentações", "Lamen."),
	EZEQUIEL("Ezequiel", "Eze."),
	DANIEL("Daniel", "Dan."),
	OSEIAS("Oseias", "Ose."),
	JOEL("Joel", "Joel"),
	AMOS("Amós", "Amós"),
	OBADIAS("Obadias", "Oba."),
	JONAS("Jonas", "Jonas"),
	MIQUEIAS("Miqueias", "Miq."),
	NAUM("Naum", "Naum"),
	HABACUQUE("Habacuque", "Haba."),
	SOFONIAS("Sofonias", "Sofo."),
	AGEU("Ageu", "Ageu"),
	ZACARIAS("Zacarias", "Zaca."),
	MALAQUIAS("Malaquias", "Mala."),
	MATEUS("Mateus", "Mat."),
	MARCOS("Marcos", "Mar."),
	LUCAS("Lucas", "Lu."),
	JOAO("João", "Jo."),
	ATOS("Atos", "At."),
	ROMANOS("Romanos", "Rom."),
	CORINTIOS1("1 Coríntios", "1 Corín."),
	CORINTIOS2("2 Coríntios", "2 Corín."),
	GALATAS("Gálatas", "Gál."),
	EFESIOS("Efésios", "Efé."),
	FILIPENSES("Filipenses", "Fili."),
	COLOSSENSES("Colossenses", "Col."),
	TESSALONICENSES1("1 Tessalonicenses", "1 Tes."),
	TESSALONICENSES2("2 Tessalonicenses", "2 Tes."),
	TIMOTEO1("1 Timóteo", "1 Tim."),
	TIMOTEO2("2 Timóteo", "2 Tim."),
	TITO("Tito", "Tito"),
	FILEMON("Filêmon", "Filê."),
	HEBREUS("Hebreus", "Heb."),
	TIAGO("Tiago", "Tia."),
	PEDRO1("1 Pedro", "1 Pe."),
	PEDRO2("2 Pedro", "2 Pe."),
	JOAO1("1 João", "1 Jo."),
	JOAO2("2 João", "2 Jo."),
	JOAO3("3 João", "3 Jo."),
	JUDAS("Judas", "Jud."),
	REVELACAO("Revelação", "Rev.");
	
	private String nome;
	private String abreviacao;
	
	private LivrosBiblicos(String nome, String abreviacao) {
		this.nome = nome;
		this.abreviacao = abreviacao;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getAbreviacao() {
		return abreviacao;
	}
}