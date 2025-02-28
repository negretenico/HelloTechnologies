from dataclasses import dataclass
from datetime import datetime
from typing import Dict, Any


@dataclass
class Message:
    header: Dict[str, Any]
    key: str
    value: Any
    timestamp: datetime
