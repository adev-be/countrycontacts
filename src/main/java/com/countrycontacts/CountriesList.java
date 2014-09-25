package com.countrycontacts;

import java.util.ArrayList;

public class CountriesList {
	private final static  Country afghanistan = new Country("Afghanistan","af","+90");
	private final static  Country afrique_du_sud = new Country("Afrique du Sud","za","+27");
	private final static  Country albanie = new Country("Albanie","al","+355");
	private final static  Country algerie = new Country("Algerie","dz","+213");
	private final static  Country allemagne = new Country("Allemagne","de","+49");
	private final static  Country andorre = new Country("Andorre","ad","+376");
	private final static  Country angola = new Country("Angola","ao","+244");
	private final static  Country arabie_saoudite = new Country("Arabie-Saoudite","sa","+966");
	private final static  Country argentine = new Country("Argentine","ar","+54");
	private final static  Country armenie = new Country("Armenie","am","+374");
	private final static  Country australie = new Country("Australie","au","+61");
	private final static  Country autriche = new Country("Autriche","at","+43");
	private final static  Country azerbaidjan = new Country("Azerbaidjan","az","+994");
	private final static  Country bahrein = new Country("Bahrein","bh","+973");
	private final static  Country bangladesh = new Country("Bangladesh","bd","+880");
	private final static  Country bielorussie = new Country("Bielorussie","by","+375");
	private final static  Country belgique = new Country("Belgique","be","+32");
	private final static  Country benin = new Country("Benin","bj","+229");
	private final static  Country bhoutan = new Country("Bhoutan","bt","+975");
	private final static  Country bolivie = new Country("Bolivie","bo","+591");
	private final static  Country bosnie_herzegovine = new Country("Bosnie-Herzegovine","ba","+387");
	private final static  Country botswana = new Country("Botswana","bw","+267");
	private final static  Country bresil = new Country("Bresil","br","+55");
	private final static  Country brunei = new Country("Brunei","bn","+673");
	private final static  Country bulgarie = new Country("Bulgarie","bg","+359");
	private final static  Country burkina_faso = new Country("Burkina-Faso","bf","+226");
	private final static  Country burundi = new Country("Burundi","bi","+257");
	private final static  Country cameroun = new Country("Cameroun","cm","+237");
	private final static  Country cambodge = new Country("Cambodge","kh","+855");
	private final static  Country canada = new Country("Canada","ca","+1");
	private final static  Country republique_centrafricaine = new Country("Republique Centrafricaine","cf","+236");
	private final static  Country chili = new Country("Chili","cl","+56");
	private final static  Country chine = new Country("Chine","cn","+86");
	private final static  Country chypre = new Country("Chypre","cy","+357");
	private final static  Country colombie = new Country("Colombie","co","+57");
	private final static  Country comores = new Country("Comores","km","+269");
	private final static  Country congo = new Country("Congo","cd","+242");
	private final static  Country coree_du_sud = new Country("Coree du Sud","kr","+82");
	private final static  Country coree_du_nord = new Country("Coree du Nord","kp","+850");
	private final static  Country costa_rica = new Country("Costa Rica","cr","+506");
	private final static  Country cote_ivoire = new Country("Cote d'Ivoire","ci","+225");
	private final static  Country croatie = new Country("Croatie","hr","+385");
	private final static  Country cuba = new Country("Cuba","cu","+53");
	private final static  Country danemark = new Country("Danemark","dk","+45");
	private final static  Country djibouti = new Country("Djibouti","dj","+253");
	private final static  Country egypte = new Country("Egypte","eg","+20");
	private final static  Country salvador = new Country("Salvador","sv","+503");
	private final static  Country emirats_arabes_unis = new Country("Emirats arabes unis","ae","+971");
	private final static  Country equateur = new Country("Equateur","ec","+593");
	private final static  Country espagne = new Country("Espagne","es","+34");
	private final static  Country estonie = new Country("Estonie","ee","+372");
	private final static  Country etats_unis = new Country("Etats-Unis","us","+1");
	private final static  Country ethiopie = new Country("Ethiopie","et","+251");
	private final static  Country fidji = new Country("Fidji","fj","+679");
	private final static  Country france = new Country("France","fr","+33");
	private final static  Country gabon = new Country("Gabon","ga","+241");
	private final static  Country gambie = new Country("Gambie","gm","+220");
	private final static  Country georgie = new Country("Georgie","ge","+995");
	private final static  Country ghana = new Country("Ghana","gh","+233");
	private final static  Country gibraltar = new Country("Gibraltar","gi","+350");
	private final static  Country grece = new Country("Grece","gr","+30");
	private final static  Country guatemala = new Country("Guatemala","gt","+502");
	private final static  Country guinee = new Country("Guinee","gn","+224");
	private final static  Country haiti = new Country("Haiti","ht","+509");
	private final static  Country honduras = new Country("Honduras","hn","+504");
	private final static  Country hong_kong = new Country("Hong Kong","hk","+852");
	private final static  Country hongrie = new Country("Hongrie","hu","+36");
	private final static  Country inde = new Country("Inde","in","+91");
	private final static  Country indonesie = new Country("Indonesie","id","+62");
	private final static  Country iran = new Country("Iran","ir","+98");
	private final static  Country irak = new Country("Irak","iq","+964");
	private final static  Country irlande = new Country("Irlande","ie","+353");
	private final static  Country islande = new Country("Islande","is","+354");
	private final static  Country israel = new Country("Israel","il","+972");
	private final static  Country italie = new Country("Italie","it","+390");
	private final static  Country japon = new Country("Japon","jp","+81");
	private final static  Country jordanie = new Country("Jordanie","jo","+962");
	private final static  Country kazakhstan = new Country("Kazakhstan","kz","+7");
	private final static  Country kenya = new Country("Kenya","ke","+254");
	private final static  Country koweit = new Country("Koweit","kw","+965");
	private final static  Country laos = new Country("Laos","la","+856");
	private final static  Country lesotho = new Country("Lesotho","ls","+266");
	private final static  Country lettonie = new Country("Lettonie","lv","+371");
	private final static  Country liban = new Country("Liban","lb","+961");
	private final static  Country liberia = new Country("Liberia","lr","+231");
	private final static  Country libye = new Country("Libye","ly","+218");
	private final static  Country liechtenstein = new Country("Liechtenstein","li","+423");
	private final static  Country lituanie = new Country("Lituanie","lt","+370");
	private final static  Country luxembourg = new Country("Luxembourg","lu","+352");
	private final static  Country macedoine = new Country("Macedoine","mk","+389");
	private final static  Country madagascar = new Country("Madagascar","mg","+261");
	private final static  Country malaisie = new Country("Malaisie","my","+60");
	private final static  Country malawi = new Country("Malawi","mw","+265");
	private final static  Country maldives = new Country("Maldives","mv","+960");
	private final static  Country mali = new Country("Mali","ml","+223");
	private final static  Country malte = new Country("Malte","mt","+356");
	private final static  Country maroc = new Country("Maroc","ma","+212");
	private final static  Country mauritanie = new Country("Mauritanie","mr","+222");
	private final static  Country mexique = new Country("Mexique","mx","+52");
	private final static  Country monaco = new Country("Monaco","mc","+377");
	private final static  Country mongoli = new Country("Mongoli","mn","+976");
	private final static  Country mozambique = new Country("Mozambique","mz","+258");
	private final static  Country birmanie = new Country("Birmanie","mm","+95");
	private final static  Country namibie = new Country("Namibie","na","+264");
	private final static  Country nepal = new Country("Nepal","np","+977");
	private final static  Country nicaragua = new Country("Nicaragua","ni","+505");
	private final static  Country niger = new Country("Niger","ne","+227");
	private final static  Country nigeria = new Country("Nigeria","ng","+234");
	private final static  Country norvege = new Country("Norvege","no","+47");
	private final static  Country nouvelle_zelande = new Country("Nouvelle-Zelande","nz","+64");
	private final static  Country oman = new Country("Oman","om","+968");
	private final static  Country ouganda = new Country("Ouganda","ug","+256");
	private final static  Country ouzbekistan = new Country("Ouzbekistan","uz","+7");
	private final static  Country pakistan = new Country("Pakistan","pk","+92");
	private final static  Country palestine = new Country("Palestine","ps","+970");
	private final static  Country panama = new Country("Panama","pa","+507");
	private final static  Country paraguay = new Country("Paraguay","py","+595");
	private final static  Country pays_bas = new Country("Pays-Bas","nl","+31");
	private final static  Country perou = new Country("Perou","pe","+51");
	private final static  Country philippines = new Country("Philippines","ph","+63");
	private final static  Country pologne = new Country("Pologne","pl","+48");
	private final static  Country portugal = new Country("Portugal","pt","+351");
	private final static  Country qatar = new Country("Qatar","qa","+974");
	private final static  Country roumanie = new Country("Roumanie","ro","+40");
	private final static  Country royaume_uni = new Country("Royaume-Uni","gb","+44");
	private final static  Country russie = new Country("Russie","ru","+7");
	private final static  Country rwanda = new Country("Rwanda","rw","+250");
	private final static  Country saint_marin = new Country("Saint-Marin","sm","+378");
	private final static  Country samoa = new Country("Samoa","ws","+685");
	private final static  Country senegal = new Country("Senegal","sn","+221");
	private final static  Country seychelles = new Country("Seychelles","sc","+248");
	private final static  Country sierra_leone = new Country("Sierra Leone","sl","+232");
	private final static  Country singapour = new Country("Singapour","sg","+65");
	private final static  Country slovaquie = new Country("Slovaquie","sk","+421");
	private final static  Country slovenie = new Country("Slovenie","si","+386");
	private final static  Country somalie = new Country("Somalie","so","+252");
	private final static  Country soudan = new Country("Soudan","sd","+249");
	private final static  Country sri_lanka = new Country("Sri Lanka","lk","+94");
	private final static  Country suede = new Country("Suede","se","+46");
	private final static  Country suisse = new Country("Suisse","ch","+41");
	private final static  Country swaziland = new Country("Swaziland","sz","+268");
	private final static  Country syrie = new Country("Syrie","sy","+963");
	private final static  Country tadjikistan = new Country("Tadjikistan","tj","+992");
	private final static  Country taiwan = new Country("Taiwan","tw","+886");
	private final static  Country tanzanie = new Country("Tanzanie","tz","+255");
	private final static  Country tchad = new Country("Tchad","td","+235");
	private final static  Country republique_tcheque = new Country("Republique tcheque","cz","+420");
	private final static  Country thailande = new Country("Thailande","th","+66");
	private final static  Country togo = new Country("Togo","tg","+228");
	private final static  Country tonga = new Country("Tonga","tg","+676");
	private final static  Country tunisie = new Country("Tunisie","tn","+216");
	private final static  Country turquie = new Country("Turquie","tr","+90");
	private final static  Country tuvalu = new Country("Tuvalu","tv","+688");
	private final static  Country ukraine = new Country("Ukraine","ua","+380");
	private final static  Country uruguay = new Country("Uruguay","uy","+598");
	private final static  Country vanuatu = new Country("Vanuatu","vu","+678");
	private final static  Country venezuela = new Country("Venezuela","ve","+58");
	private final static  Country vietnam = new Country("Vietnam","vn","+84");	
	private final static  Country wallis_et_futuna = new Country("Wallis-et-Futuna","wf","+681");
	private final static  Country yemen = new Country("Yemen","ye","+967");
	private final static  Country zambie = new Country("Zambie","zm","+260");
	private final static  Country zimbabwe = new Country("Zimbabwe","zw","+263");
			
			
	private final static Country[] countries_fr = {arabie_saoudite,bielorussie,afghanistan,afrique_du_sud,albanie,algerie,allemagne,angola,andorre,argentine,armenie,australie,autriche,
		azerbaidjan,bahrein,bangladesh,belgique,benin,bolivie,bosnie_herzegovine,botswana,bresil,brunei,bulgarie,cameroun,chili,chine,colombie,
		coree_du_sud,cote_ivoire,croatie,danemark,egypte,espagne,etats_unis,france,georgie,ghana,grece,hong_kong,hongrie,inde,indonesie,iran,irak,
		irlande,islande,israel,italie,japon,kenya,liban,libye,liechtenstein,lituanie,luxembourg,malaisie,mali,malte,maroc,mexique,nepal,niger,nigeria,
		norvege,nouvelle_zelande,pakistan,palestine,paraguay,pays_bas,perou,philippines,pologne,portugal,qatar,roumanie,royaume_uni,russie,senegal,
		singapour,slovaquie,slovenie,suede,suisse,syrie,taiwan,republique_tcheque,thailande,togo,tunisie,turquie,ukraine,uruguay,venezuela,vietnam,
		yemen,zambie,zimbabwe,bhoutan,burkina_faso,burundi,cambodge,canada,republique_centrafricaine,chypre,comores,congo,coree_du_nord,costa_rica,cuba,
		djibouti,salvador,emirats_arabes_unis,equateur,estonie,ethiopie,fidji,gabon,gambie,gibraltar,guatemala,guinee,haiti,honduras,jordanie,kazakhstan,
		koweit,laos,lesotho,lettonie,liberia,macedoine,madagascar,malawi,maldives,mauritanie,monaco,mongoli,mozambique,birmanie,namibie,nicaragua,oman,ouganda,
		ouzbekistan,panama,rwanda,saint_marin,samoa,seychelles,sierra_leone,somalie,soudan,sri_lanka,swaziland,tadjikistan,tanzanie,tchad,tonga,tuvalu,vanuatu,
		wallis_et_futuna}; 

	public static String getPrefixFromISO(String iso) {
		for (Country country : countries_fr) {
			if (country.getAlpha2().equalsIgnoreCase(iso)) {
				return country.getPrefix();
			}
		}
		return null;
	}
	
	public static String getPrefixFromName(String name) {
		for (Country country : countries_fr) {
			if (country.getName().equalsIgnoreCase(name)) {
				return country.getPrefix();
			}
		}
		return null;
	}
	
	public static String getNameFromISO(String iso) {
		for (Country country : countries_fr) {
			if (country.getAlpha2().equalsIgnoreCase(iso)) {
				return country.getName();
			}
		}
		return null;
	}
	
	public static String getNameFromPrefix(String prefix) {
		for (Country country : countries_fr) {
			if (country.getPrefix().equalsIgnoreCase(prefix)) {
				return country.getName();
			}
		}
		return null;
	}
	
	public static ArrayList<Country> getOriginCountriesOfContacts(ArrayList<Contact> contactList) {
		
		ArrayList<Country> presentCountries = new ArrayList<Country>();
		
		for(Contact contact : contactList) {
			for(Country country : countries_fr) {
				if (contact.comparePrefix(country.getPrefix())) {
					if (!presentCountries.contains(country)) {
						presentCountries.add(country);
					}
				}
			}
		}
		return presentCountries;
	}
	
	public static ArrayList<Contact> getNotPrefixedContacts(ArrayList<Country> countries, ArrayList<Contact> contacts) {
		
		ArrayList<Contact> contacts_temp = new ArrayList<Contact>();
		
		for(int i=0; i<contacts.size(); i++) {
			boolean prefixed = false;
			for(int j=0; j<countries.size(); j++) {
				//Log.d("ComparePrefix", contacts.get(i).getName()+" - "+contacts.get(i).getNumber()+" vs "+countries.get(j).getPrefix()
				//		+(contacts.get(i).comparePrefix(countries.get(j).getPrefix()) == true ? "-> TRUE" : ""));
				if (contacts.get(i).comparePrefix(countries.get(j).getPrefix())) {
					prefixed = true;
					break;
				}
			}
			if (!prefixed) contacts_temp.add(contacts.get(i));
		}
		return contacts_temp;
	}
}
