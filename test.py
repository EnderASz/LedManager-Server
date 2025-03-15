for byte in b"\x0atest":
    print(hex(byte).removeprefix("0x").zfill(2), end="")