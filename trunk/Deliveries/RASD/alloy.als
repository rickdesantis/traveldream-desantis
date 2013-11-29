module traveldream

sig wString {}
sig posInt  { value: Int } { value > 0 }
enum UserType { customer, employee }
enum Boolean { true, false }

sig User {
	id: one posInt,
	username: one wString,
	password: one wString,
	fullname: one wString,
	email: one wString,
  type: one UserType,
	packages: seq CustomizedPackage
} {
	username != email
}


abstract sig Component {
	id: one posInt,
	name: one wString,
	description: one wString,
	price: one posInt
}

sig Excursion extends Component {
	type: one wString
}

sig Hotel extends Component {
	city: one wString,
	stars: one posInt,
	address: one wString,
	telephone: one wString
}

sig Flight extends Component {
	from: one wString,
	to: one wString,
	leavingTimes: seq wString,
	company: one wString
}

sig CarRental extends Component {
	from: one wString,
	to: one wString,
	car: one wString,
	company: one wString
}

sig Train extends Component {
	from: one wString,
	to: one wString,
	leavingTimes: seq wString,
	company: one wString
}

sig Package {
	id: one wString,
	name: one wString,
	days: one posInt,
	components: seq Component
} {
  #components > 0
}

sig ActualComponent {
	date: one wString,
	component: one Component
}

sig CustomizedPackage {
	id: one wString,
	days: one posInt,
	completed: one Boolean,
	user: lone User,
	package: one Package,
	actualComponents: seq ActualComponent
} {
	#actualComponents > 0
}

// NO DUPLICATE IDs, USER, COMPONENT, PACKAGE
fact NoDuplicateId {
  no disj u1,u2: User | u1.id = u2.id
	no disj c1,c2: Component | c1.id = c2.id
	no disj p1,p2: Package | p1.id = p2.id
	no disj cp1,cp2: CustomizedPackage | cp1.id = cp2.id
}

fact NoDuplicateEntity {
	no disj u1,u2: User | u1.username = u2.username || u1.email = u2.email
	no disj c1,c2: Component | c1.name = c2.name
	no disj p1,p2: Package | p1.name = p2.name
}
assert A_NoDuplicateEntity {
	no disj u1,u2: User | u1.username = u2.username || u1.email = u2.email
	no disj c1,c2: Component | c1.name = c2.name
	no disj p1,p2: Package | p1.name = p2.name
}

check A_NoDuplicateEntity for 4

/*
// NO CUSTOMIZED PACKAGE WITH actualComponents != components IN THE PACKAGE
fact NoDiffComponents {
	all cp: CustomizedPackage {
		all a: cp.actualComponents {
			a.component in cp.package.elems
		}
	}
}
assert A_NoDiffComponents {
	all cp: CustomizedPackage {
		all a: cp.actualComponents {
			a.component in cp.package.elems
		}
	}
}

check A_NoDiffComponents for 4 */

// NO DUPLICATES IN THE SETS
fact NoDuplicateInSets {
	all cp:CustomizedPackage | ! cp.actualComponents.hasDups
	all p:Package | ! p.components.hasDups
}
assert A_NoDuplicateInSets {
	all cp:CustomizedPackage | ! cp.actualComponents.hasDups
	all p:Package | ! p.components.hasDups
}
check A_NoDuplicateInSets for 4

pred show() {
	#User > 3
	#User <= 10
	#Component > 10
	#Component <= 20
	#Package > 3
	#Package <= 5
	#CustomizedPackage > 2
	#CustomizedPackage <= 5
}
run show for 5

