package ru.sber.services
import org.springframework.stereotype.Component

@Component
class FirstService {
    override fun toString(): String {
        return "I am firstService"
    }
}

class SecondService {
    override fun toString(): String {
        return "I am secondService"
    }
}

class ThirdService {
    override fun toString(): String {
        return "I am thirdService"
    }
}

class FourthService {
    override fun toString(): String {
        return "I am fourthService"
    }
}